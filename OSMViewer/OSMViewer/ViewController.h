#import <UIKit/UIKit.h>
#import "SQLiteDatabaseMapDataSource.h"
#import "MapDataSourceFetchResultsHandler.h"

@interface ViewController : UIViewController
{
  @private
  SQLiteDatabaseMapDataSource * mapDataSource;
}
@end
