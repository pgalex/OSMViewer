#import "SQLiteDatabaseMapDataSource.h"
#import "MapBounds.h"

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
  // db not found
  
  if ([self isConnectionOpen])
  {
    [self closeConnection];
  }
  
  int openDatabaseResult = sqlite3_open([pathToDatabase UTF8String], &database);
  if (openDatabaseResult != SQLITE_OK)
  {
    // throw special type of expection
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


-(void) fetchMapObjectsInArea:(MapBounds *)area toResultHandler:(MapDataSourceFetchResultsHandler *)fetchResultsHandler
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
  // zero area
  
  NSString * fetchQeury = [NSString stringWithFormat:@"SELECT ROWID, drawingId FROM MapObjects WHERE minLatitude<=%f AND maxLatitude>=%f AND minLongitude<=%f AND maxLongitude>=%f", [area latitudeMaximum], [area latitudeMinimum], [area longitudeMaximum], [area longitudeMinimum] ];
}

@end
