package com.directsupply.MisspelledSearchTermWordCount;

import junit.framework.Assert;
import junit.framework.TestCase;

public class QueryMatcherTest extends TestCase {
    private final String RealLineFromLogWithQuery = "INFO: [advantageProductsCollection] webapp=/solr path=/select" +
            " params={facet=true&sort=score+desc,ProductNameSort+asc&f.Manufacturer.facet.mincount=1&" +
            "qf=ProductName^50+ProductDescription^20+CategoryName^1+SubCategoryName^80+ProductNumber+" +
            "ManfProdNum&version=2.2&rows=25&f.Supplier.facet.mincount=1&defType=dismax&pf=ProductName^25&" +
            "start=0&facet.query=(PreferredProductContracts:3)&facet.query=(FrequentlyPurchasedFacilities:23)&" +
            "q=(cheese+chdr+yel)&group.truncate=true&bf=if(exists(query({!v%3D'PreferredProductContracts:3'})),1,0)" +
            "^1000+if(exists(query({!v%3D'FrequentlyPurchasedFacilities:23'})),1,0)^2000&" +
            "f.SubCategory.facet.mincount=1&facet.field={!ex%3Dc,sc}Supplier&facet.field=Manufacturer&facet.field" +
            "={!ex%3Dc,sc}Category&facet.field={!ex%3Dsc}SubCategory&f.Category.facet.mincount=1&fq=((Catalogs:2)+" +
            "OR+(Catalogs:5)+OR+(Catalogs:20)+OR+(Catalogs:4))} hits=1534 status=0 QTime=1309 ";
    private final String RealLineWithSupplierFilterAfterQuery = "INFO: [advantageProductsCollection] webapp=/solr " +
            "path=/select params={facet=true&sort=ProductNameSort+desc&f.Manufacturer.facet.mincount=1&" +
            "qf=ProductName^50+ProductDescription^20+CategoryName^1+SubCategoryName^80+ProductNumber+ManfProdNum&" +
            "version=2.2&rows=25&f.Supplier.facet.mincount=1&defType=dismax&pf=ProductName^25&start=0&" +
            "facet.query=(PreferredProductContracts:3)&facet.query=(FrequentlyPurchasedFacilities:1456)&" +
            "q=(vanilla)&group.truncate=true&bf=if(exists(query({!v%3D'PreferredProductContracts:3'})),1,0)^1000+" +
            "if(exists(query({!v%3D'FrequentlyPurchasedFacilities:1456'})),1,0)^2000&f.SubCategory.facet.mincount=1" +
            "&facet.field={!ex%3Dc,sc}Supplier&facet.field=Manufacturer&facet.field={!ex%3Dc,sc}Category&" +
            "facet.field={!ex%3Dsc}SubCategory&f.Category.facet.mincount=1&fq=((Catalogs:27)+OR+(Catalogs:2))&" +
            "fq=(SupplierID:87)&fq={!tag%3Dc}CategoryID:6695&fq=(FrequentlyPurchasedFacilities:1456)} hits=3 " +
            "status=0 QTime=4 ";
    private final String RealLineFromClassicSearchResults = "38426795\tAR\t534\t2011-04-27T06:37:34\tGET\t200\t" +
            "/dssi.3.30/Search/Results.aspx\tSrtBy=&Term=CPG-AP202&PID=1&RcPerPg=20&Supp=SSWD&SrtOrd=1&OnOG=" +
            "True&OffOG=False&POH=0&Cat=&Spec=2";
    private final String RealLineFromClassicFood = "47161483\tDS\t380\t2012-08-07T03:16:50\tGET\t200\t" +
            "/dssi.3.30/Food/Search.aspx\tSearchString=CHEESE%20balls";
    private final String BugRealLineWithUnicodeProblem = "52453405\tRH\t94\t2013-05-21T08:43:35\tGET\t" +
            "200\t/dssi.3.30/Search/Results.aspx\tSrtBy=&" +
            "Term=%20Hayden%u2122%20Laminate%20Bookcase%2C%203-shelf%2C%20Hilton%20Cherry&PID=1&RcPerPg=20&" +
            "Supp=&SrtOrd=1&OnOG=true&OffOG=true&POH=0&Cat=&Spec=2";

    public void testExtractSearchQuery() throws Exception {
        final String expected = "cheese chdr yel";
        String actual = QueryMatcher.extractSearchQuery(RealLineFromLogWithQuery);
        Assert.assertEquals(expected, actual);
    }

    public void testExtractSearchQueryWithSupplierFilter() throws Exception {
        final String expected = "vanilla";
        String actual = QueryMatcher.extractSearchQuery(RealLineWithSupplierFilterAfterQuery);
        Assert.assertEquals(expected, actual);
    }

    public void testExtractSearchQueryReturnsNullWhenQueryNotFound() throws Exception {
        Assert.assertNull(QueryMatcher.extractSearchQuery("no query here"));
    }

    public void testExtractSearchQueryFromClassicShoppingCart() throws Exception {
        final String expected = "cpg-ap202";
        String actual = QueryMatcher.extractSearchQuery(RealLineFromClassicSearchResults);
        Assert.assertEquals(expected, actual);
    }

    public void testExtractSearchQueryFromClassicFoodSearch() throws Exception {
        final String expected = "cheese balls";
        String actual = QueryMatcher.extractSearchQuery(RealLineFromClassicFood);
        Assert.assertEquals(expected, actual);
    }

    public void testExtractSearchQueryFromUnicodeBugLine() throws Exception {
        final String expected = "hayden(tm) laminate bookcase, 3-shelf, hilton cherry";
        String actual = QueryMatcher.extractSearchQuery(BugRealLineWithUnicodeProblem);
        Assert.assertEquals(expected, actual);
    }
}
