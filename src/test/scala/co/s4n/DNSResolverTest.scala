package co.s4n

import org.scalatest.FunSuite

class DNSResolverTest extends FunSuite {

  test( "Test against campestre.edu.co" ) {
    val mxRecord = DNSResolver.mxRecordFrom( "campestre.edu.co" )
    info( s"MX record = ${mxRecord}" )
    assert( mxRecord.isDefined )
  }

  test( "Test against bvc.com.co" ) {
    val mxRecord = DNSResolver.mxRecordFrom( "bvc.com.co" )
    info( s"MX record = ${mxRecord}" )
    assert( mxRecord.isDefined )
  }

  test( "Test against superfinanciera.gov.co" ) {
    val mxRecord = DNSResolver.mxRecordFrom( "superfinanciera.gov.co" )
    info( s"MX record = ${mxRecord}" )
    assert( mxRecord.isDefined )
  }

  test( "Test against gmx.net" ) {
    val mxRecord = DNSResolver.mxRecordFrom( "gmx.net" )
    info( s"MX record = ${mxRecord}" )
    assert( mxRecord.isDefined )
  }

  test( "Test against green.ch" ) {
    val mxRecord = DNSResolver.mxRecordFrom( "green.ch" )
    info( s"MX record = ${mxRecord}" )
    assert( mxRecord.isDefined )
  }

  test( "Test against tschannen.cc" ) {
    val mxRecord = DNSResolver.mxRecordFrom( "tschannen.cc" )
    info( s"MX record = ${mxRecord}" )
    assert( mxRecord.isDefined )
  }

  test( "Test against bancolombia.com" ) {
    val mxRecord = DNSResolver.mxRecordFrom( "bancolombia.com" )
    info( s"MX record = ${mxRecord}" )
    assert( mxRecord.isDefined )
  }

  test( "Test against libertycolombia.com" ) {
    val mxRecord = DNSResolver.mxRecordFrom( "libertycolombia.com" )
    info( s"MX record = ${mxRecord}" )
    assert( mxRecord.isDefined )
  }

  test( "Test against corredores.com" ) {
    val mxRecord = DNSResolver.mxRecordFrom( "corredores.com" )
    info( s"MX record = ${mxRecord}" )
    assert( mxRecord.isDefined )
  }

  test( "Test against carvajal.com" ) {
    val mxRecord = DNSResolver.mxRecordFrom( "carvajal.com" )
    info( s"MX record = ${mxRecord}" )
    assert( mxRecord.isDefined )
  }

  test( "Test against mti.com.co" ) {
    val mxRecord = DNSResolver.mxRecordFrom( "mti.com.co" )
    info( s"MX record = ${mxRecord}" )
    assert( mxRecord.isDefined )
  }

  test( "Test against ecopetrol.com.co" ) {
    val mxRecord = DNSResolver.mxRecordFrom( "ecopetrol.com.co" )
    info( s"MX record = ${mxRecord}" )
    assert( mxRecord.isDefined )
  }

  test( "Test against ccb.org.co" ) {
    val mxRecord = DNSResolver.mxRecordFrom( "ccb.org.co" )
    info( s"MX record = ${mxRecord}" )
    assert( mxRecord.isDefined )
  }

  test( "Test against coris.com.co" ) {
    val mxRecord = DNSResolver.mxRecordFrom( "coris.com.co" )
    info( s"MX record = ${mxRecord}" )
    assert( mxRecord.isDefined )
  }

}
