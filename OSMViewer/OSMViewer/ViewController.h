#import <UIKit/UIKit.h>
#import "SQLiteDatabaseMapDataSource.h"
#import "MapDataSourceFetchResultsHandler.h"

@interface ViewController : UIViewController<MapDataSourceFetchResultsHandler>
{
  @private
  SQLiteDatabaseMapDataSource * mapDataSource;
}
@end
