#import <Foundation/Foundation.h>

@class MapDataSourceFetchResultsHandler;
@class MapBounds;

@protocol MapDataSource <NSObject>

@required
/**
 Fetch all map objects that exists in area and give them to result handler
 \param area - area using to deterimine which map objects need to fetch. Must be not nil
 \param fetchResultsHandler - handler of fetching results. Must be not nil
 */
-(void) fetchMapObjectsInArea:(MapBounds *)area toResultHandler:(MapDataSourceFetchResultsHandler *)fetchResultsHandler;

@end
