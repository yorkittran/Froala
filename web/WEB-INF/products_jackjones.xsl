<?xml version="1.0" encoding="UTF-8"?>

<!--
    Document   : products.xsl
    Created on : July 3, 2020, 6:43 PM
    Author     : Yorkit Tran
    Description:
        Purpose of transformation follows.
-->

<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
    <xsl:output method="xml"/>
    <xsl:template match="/">
        <xsl:variable name="priceStr" select="//*[@class='nonsticky-price__container--visible']/em"/>
        <xsl:variable name="imageStr" select="//*[@class='product-images__main__image' and position()=1]/img/@data-src"/>
        <products>
            <id>0</id>
            <categoryid>0</categoryid>
            <name>
                <xsl:value-of select="//*[@class='product-name--visible']"/>
            </name>
            <colour>colour</colour>
            <description>
                <xsl:for-each select="//*[@class='pdp-description__text js-hide__display']//text()">
                    <xsl:value-of select="." />
                </xsl:for-each>
            </description>
            <image>
                <xsl:value-of select="//*[@class='product-images__main__image' and position()=1]/img/@data-src" />
            </image>
            <url>url</url>
            <recommend>0</recommend>
        </products>
    </xsl:template>
</xsl:stylesheet>
