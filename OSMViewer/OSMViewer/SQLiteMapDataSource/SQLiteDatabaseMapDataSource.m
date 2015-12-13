#import "SQLiteDatabaseMapDataSource.h"
#import "MapBounds.h"
#import "SimpleLocation.h"

@implementation SQLiteDatabaseMapDataSource

-(id) init
{
  self = [super init];
  if (self != nil)
  {
    database = nil;
  }
  return self;
}


-(void) connectToDatabase:(NSString * )pathToDatabase
{
  if (pathToDatabase == nil)
  {
    @throw [NSException exceptionWithName:@"pathToDatabase is nil" reason:nil userInfo:nil];
  }
  // file not found
  
  if ([self isConnectionOpen])
  {
    [self closeConnection];
  }
  
  int openDatabaseResult = sqlite3_open([pathToDatabase UTF8String], &database);
  if (openDatabaseResult != SQLITE_OK)
  {
    // throw special type of expection
    database = nil;
  }
}


-(BOOL) isConnectionOpen;
{
  return database != nil;
}


-(void) closeConnection
{
  if (database == nil)
    return;
  
  sqlite3_close(database);
  database = nil;
}


-(void) fetchMapObjectsInArea:(MapBounds *)area toResultHandler:(id<MapDataSourceFetchResultsHandler>)fetchResultsHandler
{
  if (area == nil)
  {
    @throw [NSException exceptionWithName:@"area is nil" reason:nil userInfo:nil];
  }
  if (fetchResultsHandler == nil)
  {
    @throw [NSException exceptionWithName:@"fetchResultsHandler is nil" reason:nil userInfo:nil];
  }
  
  if (![self isConnectionOpen])
  {
    return;
  }
  if ([area isZero])
  {
    return;
  }
  
  sqlite3_stmt * fetchMapObjectsStatement;
  NSString * fetchQeury = [NSString stringWithFormat:@"SELECT ROWID, drawingId FROM MapObjects WHERE minLatitude<=%f AND maxLatitude>=%f AND minLongitude<=%f AND maxLongitude>=%f", [area latitudeMaximum], [area latitudeMinimum], [area longitudeMaximum], [area longitudeMinimum] ];
  int statementPrepareResult = sqlite3_prepare_v2(database, [fetchQeury UTF8String], -1, &fetchMapObjectsStatement, NULL);
  if (statementPrepareResult == SQLITE_OK)
  {
    while (sqlite3_step(fetchMapObjectsStatement) == SQLITE_ROW)
    {
      //todo use long type for rowId
      int rowId = sqlite3_column_int(fetchMapObjectsStatement, 0);
      NSString * drawingId = [[NSString alloc] initWithUTF8String:(const char *) sqlite3_column_text(fetchMapObjectsStatement, 1)];
      NSArray * points = [self selectPointsOfMapObjectWithRowId:rowId];
      if ([points count] > 0)
      {
        [fetchResultsHandler takeMapObjectWithUniqueId:rowId drawingId:drawingId points:points];
      }
    }
    sqlite3_finalize(fetchMapObjectsStatement);
  }
  else
  {
    @throw [NSException exceptionWithName:@"Error while fetching map objects" reason:@"Can not prepare statement" userInfo:nil];
  }
}


/**
 Select points, defining map objects position, by its ROWID.
 Connection to database must be open.
 \return array of SimpleLocation defining map object position. Empty if there is no points found
 */
-(NSArray *) selectPointsOfMapObjectWithRowId:(long)mapObjectRowId
{
  if (![self isConnectionOpen])
  {
    @throw [NSException exceptionWithName:@"No connection to database" reason:nil userInfo:nil];
  }
  
  NSMutableArray * mapObjectPoints = [[NSMutableArray alloc] init];
  
  sqlite3_stmt * fetchPointsStatement;
  NSString * fetchQeury = [NSString stringWithFormat:@"SELECT latitude,longitude FROM Points WHERE objectId=%ld", mapObjectRowId];
  int statementPrepareResult = sqlite3_prepare_v2(database, [fetchQeury UTF8String], -1, &fetchPointsStatement, NULL);
  if (statementPrepareResult == SQLITE_OK)
  {
    while (sqlite3_step(fetchPointsStatement) == SQLITE_ROW)
    {
      double latitude = sqlite3_column_double(fetchPointsStatement, 0);
      double longitude = sqlite3_column_double(fetchPointsStatement, 1);
      SimpleLocation * fetchedPoint = [[SimpleLocation alloc] initWithLatitude:latitude longitude:longitude];
      [mapObjectPoints addObject:fetchedPoint];
    }
    sqlite3_finalize(fetchPointsStatement);
  }
  return mapObjectPoints;
}

@end
