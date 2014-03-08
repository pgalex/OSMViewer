//
//  ViewController.m
//  OSMViewer
//
//  Created by Александр Преображенцев on 06.03.14.
//  Copyright (c) 2014 Александр Преображенцев. All rights reserved.
//

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
