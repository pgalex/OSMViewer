#import "MapBounds.h"

@implementation MapBounds

@synthesize latitudeMinimum, latitudeMaximum, longitudeMinimum, longitudeMaximum;


-(id) initWithLatitudeMinimum:(double)latMin latitudeMaximum:(double)latMax longitudeMinimum:(double)lonMin longitudeMaximum:(double)lonMax
{
  self = [super init];
  if (self != nil)
  {
    //todo swap if needed
    latitudeMinimum = latMin;
    latitudeMaximum = latMax;
    longitudeMinimum = lonMin;
    longitudeMaximum = lonMax;
  }
  return self;
}


-(id) init
{
  return [self initWithLatitudeMinimum:0.0 latitudeMaximum:0.0 longitudeMinimum:0.0 longitudeMaximum:0.0];
}


-(BOOL) isZero
{
  // todo tests
  return (fabs(latitudeMinimum - latitudeMaximum) < 0.00001) || (fabs(longitudeMinimum - longitudeMaximum) < 0.00001);
}

@end
