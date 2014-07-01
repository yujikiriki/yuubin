package co.s4n

import org.scalatest.FunSuite

class DNSResolverTest extends FunSuite {

  test( "Test against gmx.net" ) {
    val mxRecords = DNSResolver.mxRecordsFrom( "gmx.net" )
    mxRecords foreach ( record => info( s"Record = ${record}" ) )
    assert( mxRecords.size > 0 )
  }

  test( "Test against green.ch" ) {
    val mxRecords = DNSResolver.mxRecordsFrom( "green.ch" )
    mxRecords foreach ( record => info( s"Record = ${record}" ) )
    assert( mxRecords.size > 0 )
  }

  test( "Test against tschannen.cc" ) {
    val mxRecords = DNSResolver.mxRecordsFrom( "tschannen.cc" )
    mxRecords foreach ( record => info( s"Record = ${record}" ) )
    assert( mxRecords.size > 0 )
  }
}
