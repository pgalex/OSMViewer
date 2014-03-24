#import <Foundation/Foundation.h>
#import <sqlite3.h>
#import "MapDataSource.h"

/**
 Map data source, getting information from SQLite database
 */
@interface SQLiteDatabaseMapDataSource : NSObject <MapDataSource>
{
  @private
  /// Connection to map database. Nil if there is no connection to database
  sqlite3 * database;
}

/**
 Initialize as not connected to database
 */
-(id) init;


/**
 Connect to database with given path
 \param pathToDatabase path to database. Must be not nil, file must exists
 */
-(void) connectToDatabase:(NSString * )pathToDatabase;


/**
 Close connection to database
 */
-(void) closeConnection;


/**
 Fetch all map objects that exists in area and give them to fetch result handler
 \param area - area using to deterimine which map objects need to fetch. Must be not nil
 \param fetchResultsHandler - handler of fetching results. Must be not nil
 */
-(void) fetchMapObjectsInArea:(MapBounds *)area toResultHandler:(MapDataSourceFetchResultsHandler *)fetchResultsHandler;

@end
