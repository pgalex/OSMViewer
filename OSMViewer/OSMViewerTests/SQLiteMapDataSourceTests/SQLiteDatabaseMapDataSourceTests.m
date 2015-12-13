#import <UIKit/UIKit.h>
#import <XCTest/XCTest.h>
#import "SQLiteDatabaseMapDataSource.h"

@interface SQLiteDatabaseMapDataSourceTests : XCTestCase

+(NSString *) getTestDatabaseFilePath;

@end


@implementation SQLiteDatabaseMapDataSourceTests

+(NSString *) getTestDatabaseFilePath
{
  NSBundle * bundle = [NSBundle bundleForClass:[self class]];
  return [bundle pathForResource:@"testDatabase" ofType:@"db"];
}


-(void) testConnectionClosedAfterInit
{
  SQLiteDatabaseMapDataSource * sqliteDataSource = [[SQLiteDatabaseMapDataSource alloc] init];
  
  XCTAssertFalse([sqliteDataSource isConnectionOpen]);
}


-(void) testExceptionConnectingWithNilPathToDatabase
{
  SQLiteDatabaseMapDataSource* sqliteDataSource = [[SQLiteDatabaseMapDataSource alloc] init];
  @try
  {
    [sqliteDataSource connectToDatabase:nil];
    XCTFail();
	}
	@catch(NSException * exception)
	{
    // ok
	}
}


//connectingToNotExistsDatabase

//connectingToInvalidDatabase


-(void) testConnectingToValidDatabase
{
  SQLiteDatabaseMapDataSource * sqliteDataSource = [[SQLiteDatabaseMapDataSource alloc] init];
  
  NSString * testDatabaseFilePath = [SQLiteDatabaseMapDataSourceTests getTestDatabaseFilePath];
  [sqliteDataSource connectToDatabase:testDatabaseFilePath];
  
  XCTAssertTrue([sqliteDataSource isConnectionOpen]);
  
  [sqliteDataSource closeConnection];
}


//closingConnection

//fetchingWithNilArea

//fetchingWithNilResultsHandler

//fetchingNormalWork

@end