#import "ViewController.h"

@interface ViewController ()

@end

@implementation ViewController

- (void)viewDidLoad
{
  [super viewDidLoad];
  
  mapDataSource = [[SQLiteDatabaseMapDataSource alloc] init];
  [mapDataSource connectToDatabase:@"/users/pgalex/documents/temp/RU-MOS.db"];
  [mapDataSource closeConnection];
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

@end
