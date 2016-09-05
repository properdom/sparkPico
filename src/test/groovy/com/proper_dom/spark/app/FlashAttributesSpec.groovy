package com.proper_dom.spark.app

import spock.lang.Specification

class FlashAttributesSpec extends Specification {

    def "loads attributes from cookie removing it in the process"() {
        given:
        CookieJar cookieJar = Mock()
        cookieJar.get(FlashAttributes.COOKIE_NAME) >> """{
            "name1" : "value1",
            "name2" : "value2"
        }"""
        FlashAttributes flashAttributes = new FlashAttributes(cookieJar)

        when:
        flashAttributes.load()

        then:
        flashAttributes.get('name1') == 'value1'
        flashAttributes.get('name2') == 'value2'
        1 * cookieJar.remove(FlashAttributes.COOKIE_NAME)
    }

    def "loads from empty cookie"() {
        given:
        CookieJar cookieJar = Mock()
        cookieJar.get(FlashAttributes.COOKIE_NAME) >> ''
        FlashAttributes flashAttributes = new FlashAttributes(cookieJar)

        when:
        flashAttributes.load()

        then:
        flashAttributes.isEmpty()
    }

    def "writes out to cookie"() {
        given:
        CookieJar cookieJar = Mock()
        FlashAttributes flashAttributes = new FlashAttributes(cookieJar)
        flashAttributes.put('name3', 'value3')
        flashAttributes.put('name4', 'value4')

        when:
        flashAttributes.store()

        then:
        1 * cookieJar.add(FlashAttributes.COOKIE_NAME, '{"name4":"value4","name3":"value3"}')

    }

    def "empty attributes are not written out to cookie"() {
        given:
        CookieJar cookieJar = Mock()
        FlashAttributes flashAttributes = new FlashAttributes(cookieJar)

        when:
        flashAttributes.store()

        then:
        0 * cookieJar.add(FlashAttributes.COOKIE_NAME, _)

    }
}
