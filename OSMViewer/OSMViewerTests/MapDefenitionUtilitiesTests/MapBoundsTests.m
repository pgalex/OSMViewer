#import <XCTest/XCTest.h>
#import "MapBounds.h"
@interface MapBoundsTests : XCTestCase

@end

@implementation MapBoundsTests

- (void)testInitializingWithBoundsValues
{
  MapBounds * bounds = [[MapBounds alloc] initWithLatitudeMinimum:-5.0 latitudeMaximum:8.0 longitudeMinimum:3.0 longitudeMaximum:9.0];
  XCTAssertEqualWithAccuracy(-5.0, [bounds latitudeMinimum], 0.01);
  XCTAssertEqualWithAccuracy(8.0, [bounds latitudeMaximum], 0.01);
  XCTAssertEqualWithAccuracy(3.0, [bounds longitudeMinimum], 0.01);
  XCTAssertEqualWithAccuracy(9.0, [bounds longitudeMaximum], 0.01);
}

@end
