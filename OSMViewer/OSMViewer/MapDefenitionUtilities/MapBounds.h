#import <Foundation/Foundation.h>

/**
 Describes area on map
 */
@interface MapBounds : NSObject

@property (nonatomic, assign, readonly) double latitudeMinimum;
@property (nonatomic, assign, readonly) double latitudeMaximum;
@property (nonatomic, assign, readonly) double longitudeMinimum;
@property (nonatomic, assign, readonly) double longitudeMaximum;

/**
 Init with minimum and maximum bounds of area
 */
-(id) initWithLatitudeMinimum:(double)latMin latitudeMaximum:(double)latMax longitudeMinimum:(double)lonMin longitudeMaximum:(double)lonMax;


/**
 Init with 0.0 bounds values
 */
-(id) init;


/**
 
 */
-(BOOL) isZero;
@end
