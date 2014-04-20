#import "ViewController.h"
#import "MapBounds.h"
@interface ViewController ()

@end

@implementation ViewController

- (void)viewDidLoad
{
  [super viewDidLoad];
  
  mapDataSource = [[SQLiteDatabaseMapDataSource alloc] init];
  NSString * pathToTestDatabase = [[NSBundle mainBundle] pathForResource:@"RU-MOS" ofType:@"db"];
  [mapDataSource connectToDatabase:pathToTestDatabase];
  MapBounds * someBounds = [[MapBounds alloc] initWithLatitudeMinimum:55.20294 latitudeMaximum:55.19714 longitudeMinimum:38.59708 longitudeMaximum:38.61393];
  [mapDataSource fetchMapObjectsInArea:someBounds toResultHandler:self];
  [mapDataSource closeConnection];
}


/**
 Take data of fetched map object
 \param uniqueId map unique map object identifier
 \param drawingId map object drawing identifier. Must be not nil
 \param points array of SimpleLocation, defining map object position. Must be not nil, not empty, not contains nil
 */
-(void) takeMapObjectWithUniqueId:(long)uniqueId drawingId:(NSString *)drawingId points:(NSArray *)points
{
  NSLog(@"id: %ld, drawingid: %@, pointsCount: %lu", uniqueId, drawingId, (unsigned long)[points count]);
}


- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

@end
