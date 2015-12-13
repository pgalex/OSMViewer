#import <Foundation/Foundation.h>

@protocol MapDataSourceFetchResultsHandler <NSObject>
@required
/**
 Take data of fetched map object
 \param uniqueId unique map object identifier
 \param drawingId map object drawing identifier. Must be not nil
 \param points array of SimpleLocation, defining map object position. Must be not nil, not empty, not contains nil
 */
-(void) takeMapObjectWithUniqueId:(long)uniqueId drawingId:(NSString *)drawingId points:(NSArray *)points;
@end
