#import "ViewController.h"
#import "MapBounds.h"
@interface ViewController ()

@end

@implementation ViewController

- (void)viewDidLoad
{
  [super viewDidLoad];
  
  mapDataSource = [[SQLiteDatabaseMapDataSource alloc] init];
  [mapDataSource connectToDatabase:@"/users/pgalex/documents/temp/RU-MOS.db"];
 // MapBounds * someBounds = [[MapBounds alloc] initWithLatitudeMinimum:55.20294 latitudeMaximum:55.19714 longitudeMinimum:38.59708 longitudeMaximum:38.61393];
 // [mapDataSource fetchMapObjectsInArea:someBounds toResultHandler:self];
  [mapDataSource closeConnection];
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

@end
