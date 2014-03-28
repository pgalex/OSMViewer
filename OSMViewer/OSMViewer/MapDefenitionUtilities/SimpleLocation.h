#import <Foundation/Foundation.h>

/**
 Location on map
 */
@interface SimpleLocation : NSObject

@property (nonatomic, assign) double latitude;
@property (nonatomic, assign) double longitude;

/**
 Init with given latitude and longitude
 */
-(id) initWithLatitude:(double)lat longitude:(double)lon;


/**
 Init with 0.0 bounds latitude and longitude
 */
-(id) init;

@end
