#import <XCTest/XCTest.h>
#import "MapBounds.h"

@interface MapBoundsTests : XCTestCase

@end

@implementation MapBoundsTests

-(void) testNotSwappingNormalBounds
{
  MapBounds * bounds = [[MapBounds alloc] initWithLatitudeMinimum:-5.0 latitudeMaximum:8.0 longitudeMinimum:3.0 longitudeMaximum:9.0];
  XCTAssertEqualWithAccuracy(-5.0, [bounds latitudeMinimum], 0.01);
  XCTAssertEqualWithAccuracy(8.0, [bounds latitudeMaximum], 0.01);
  XCTAssertEqualWithAccuracy(3.0, [bounds longitudeMinimum], 0.01);
  XCTAssertEqualWithAccuracy(9.0, [bounds longitudeMaximum], 0.01);
}


-(void) testSwappingInvertedBounds
{
  MapBounds * bounds = [[MapBounds alloc] initWithLatitudeMinimum:8.0 latitudeMaximum:-5.0 longitudeMinimum:9.0 longitudeMaximum:3.0];
  XCTAssertEqualWithAccuracy(-5.0, [bounds latitudeMinimum], 0.01);
  XCTAssertEqualWithAccuracy(8.0, [bounds latitudeMaximum], 0.01);
  XCTAssertEqualWithAccuracy(3.0, [bounds longitudeMinimum], 0.01);
  XCTAssertEqualWithAccuracy(9.0, [bounds longitudeMaximum], 0.01);
}


-(void) testIsZeroAllBoundsEqual
{
  MapBounds * testBounds = [[MapBounds alloc] initWithLatitudeMinimum:0.00001 latitudeMaximum:0.00001 longitudeMinimum:0.00001 longitudeMaximum:0.00001];
  XCTAssertTrue([testBounds isZero]);
}


-(void) testIsZeroLongitudeBoundsEqualMoreZero
{
  MapBounds * testBounds = [[MapBounds alloc] initWithLatitudeMinimum:1 latitudeMaximum:2 longitudeMinimum:3 longitudeMaximum:3];
  XCTAssertTrue([testBounds isZero]);
}


-(void) testIsZeroLongitudeBoundsEqualLessZero
{
  MapBounds * testBounds = [[MapBounds alloc] initWithLatitudeMinimum:1 latitudeMaximum:2 longitudeMinimum:-3 longitudeMaximum:-3];
  XCTAssertTrue([testBounds isZero]);
}


-(void) testIsZeroLatitudeBoundsEqualMoreZero
{
  MapBounds * testBounds = [[MapBounds alloc] initWithLatitudeMinimum:1 latitudeMaximum:1 longitudeMinimum:2 longitudeMaximum:3];
  XCTAssertTrue([testBounds isZero]);
}


-(void) testIsZeroLatitudeBoundsEqualLessZero
{
  MapBounds * testBounds = [[MapBounds alloc] initWithLatitudeMinimum:-1 latitudeMaximum:-1 longitudeMinimum:2 longitudeMaximum:3];
  XCTAssertTrue([testBounds isZero]);
}


-(void) testIsZeroAllBoundsNotEqual
{
  MapBounds * testBounds = [[MapBounds alloc] initWithLatitudeMinimum:0.00002 latitudeMaximum:0.00001 longitudeMinimum:0.00003 longitudeMaximum:0.00004];
  XCTAssertFalse([testBounds isZero]);
}


-(void) testIsZeroLatitudeAndLongitudeBoundsSame
{
  MapBounds * testBounds = [[MapBounds alloc] initWithLatitudeMinimum:1 latitudeMaximum:0 longitudeMinimum:1 longitudeMaximum:0];
  XCTAssertFalse([testBounds isZero]);
}

@end
