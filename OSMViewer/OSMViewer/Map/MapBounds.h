#import <Foundation/Foundation.h>

@interface MapBounds : NSObject

@property (nonatomic, assign, readonly) double latitudeMinimum;
@property (nonatomic, assign, readonly) double latitudeMaximum;
@property (nonatomic, assign, readonly) double longitudeMinimum;
@property (nonatomic, assign, readonly) double longitudeMaximum;
@end
