#import "SimpleLocation.h"

@implementation SimpleLocation

@synthesize latitude, longitude;

/**
 Init with given latitude and longitude
 */
-(id) initWithLatitude:(double)lat longitude:(double)lon
{
  self = [super init];

  if (self != nil)
  {
    latitude = lat;
    longitude = lon;
  }
  
  return self;
}


/**
 Init with 0.0 bounds latitude and longitude
 */
-(id) init
{
  return [self initWithLatitude:0.0 longitude:0.0];
}

@end
