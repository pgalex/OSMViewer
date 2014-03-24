#import "MapBounds.h"

@implementation MapBounds

@synthesize latitudeMinimum, latitudeMaximum, longitudeMinimum, longitudeMaximum;


-(id) initWithLatitudeMinimum:(double)latMin latitudeMaximum:(double)latMax longitudeMinimum:(double)lonMin longitudeMaximum:(double)lonMax
{
  self = [super init];
  if (self != nil)
  {
    latitudeMinimum = latMin;
    latitudeMaximum = latMax;
    longitudeMinimum = lonMin;
    longitudeMaximum = lonMax;
    
    [self swapBoundsIfNeeded];
  }
  return self;
}


-(void) swapBoundsIfNeeded
{
  if (latitudeMinimum > latitudeMaximum)
  {
    [self swapLatitude];
  }
  if (longitudeMinimum > longitudeMaximum)
  {
    [self swapLongitude];
  }
}


-(void) swapLatitude
{
  double temp = latitudeMinimum;
  latitudeMinimum = latitudeMaximum;
  latitudeMaximum = temp;
}


-(void) swapLongitude
{
  double temp = longitudeMinimum;
  longitudeMinimum = longitudeMaximum;
  longitudeMaximum = temp;
}


-(id) init
{
  return [self initWithLatitudeMinimum:0.0 latitudeMaximum:0.0 longitudeMinimum:0.0 longitudeMaximum:0.0];
}


-(BOOL) isZero
{
  return (fabs(latitudeMinimum - latitudeMaximum) < 0.0000001) || (fabs(longitudeMinimum - longitudeMaximum) < 0.0000001);
}

@end
