#import <UIKit/UIKit.h>
#import <XCTest/XCTest.h>
#import "SQLiteDatabaseMapDataSource.h"

@interface SQLiteDatabaseMapDataSourceTests : XCTestCase

@end

@implementation SQLiteDatabaseMapDataSourceTests

-(void) testConnectionClosedAfterInit
{
  SQLiteDatabaseMapDataSource * sqliteDataSource = [[SQLiteDatabaseMapDataSource alloc] init];
  
  XCTAssertFalse([sqliteDataSource isConnectionOpen]);
}


/*-(void) testConnectingWithNilPathToDatabase
 {
 SQLiteDatabaseMapDataSource* sqliteDataSource = [[SQLiteDatabaseMapDataSource alloc] init];
 @try
	{
	[sqliteDataSource connectToDatabase:nil];
	XCTFail();
	}
	@catch ...
	{
 // ok
	}
 }*/


//connectingToNotExistsDatabase

//connectingToInvalidDatabase

//connectingToValidDatabase (isConnectionOpen)


//closingConnection

//fetchingWithNilArea

//fetchingWithNilResultsHandler

//fetchingNormalWork

@end