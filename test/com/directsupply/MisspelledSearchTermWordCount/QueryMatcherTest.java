package com.directsupply.MisspelledSearchTermWordCount;

import junit.framework.Assert;
import junit.framework.TestCase;
import org.junit.Test;

public class QueryMatcherTest extends TestCase {
    private final String RealLineFromLogWithQuery = "INFO: [advantageProductsCollection] webapp=/solr path=/select params={facet=true&sort=score+desc,ProductNameSort+asc&f.Manufacturer.facet.mincount=1&qf=ProductName^50+ProductDescription^20+CategoryName^1+SubCategoryName^80+ProductNumber+ManfProdNum&version=2.2&rows=25&f.Supplier.facet.mincount=1&defType=dismax&pf=ProductName^25&start=0&facet.query=(PreferredProductContracts:3)&facet.query=(FrequentlyPurchasedFacilities:23)&q=(cheese+chdr+yel)&group.truncate=true&bf=if(exists(query({!v%3D'PreferredProductContracts:3'})),1,0)^1000+if(exists(query({!v%3D'FrequentlyPurchasedFacilities:23'})),1,0)^2000&f.SubCategory.facet.mincount=1&facet.field={!ex%3Dc,sc}Supplier&facet.field=Manufacturer&facet.field={!ex%3Dc,sc}Category&facet.field={!ex%3Dsc}SubCategory&f.Category.facet.mincount=1&fq=((Catalogs:2)+OR+(Catalogs:5)+OR+(Catalogs:20)+OR+(Catalogs:4))} hits=1534 status=0 QTime=1309 ";

    @Test
    public void testExtractSearchQuery() throws Exception {
        final String expected = "cheese+chdr+yel";
        String actual = QueryMatcher.extractSearchQuery(RealLineFromLogWithQuery);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testExtractSearchQueryReturnsNullWhenQueryNotFound() throws Exception {
        Assert.assertNull(QueryMatcher.extractSearchQuery("no query here"));
    }
}
