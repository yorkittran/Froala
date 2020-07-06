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
        <xsl:variable name="priceStr" select="//*[@class='ProductMeta__Price Price u-h4' or @class='ProductMeta__Price Price Price--highlight u-h4']"/>
        <xsl:variable name="imageStr" select="//*[@class='Product__SlideshowNavScroller']/a[1]/img/@src"/>
        <products>
            <id>0</id>
            <categoryid>0</categoryid>
            <name>
                <xsl:value-of select="//*[@class='ProductMeta__Title Heading u-h2']"/>
            </name>
            <colour>
                <xsl:value-of select="//*[@class='ProductMeta__Colour']"/>
            </colour>
            <description>
                <xsl:for-each select="(//*[@class='Rte'])[1]//text()">
                    <xsl:value-of select="."/>
                </xsl:for-each>
            </description>
            <image>
                <xsl:value-of select="concat(substring-before($imageStr,'_160x'),substring-after($imageStr,'_160x'))" />
            </image>
            <url>url</url>
            <recommend>0</recommend>
        </products>
    </xsl:template>
</xsl:stylesheet>
