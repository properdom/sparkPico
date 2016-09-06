package com.proper_dom.spark.app

import spark.Request
import spark.Response
import spock.lang.Specification

class CookieJarSpec extends Specification {

    def "can get cookie"() {
        given:
        Request request = Mock()
        Response response = Mock()
        CookieJar cookieJar = new CookieJar(request, response)
        request.cookie('name1') >> 'value1'

        expect:
        cookieJar.get('name1') == 'value1'
    }

    def "can set cookie"() {
        given:
        Request request = Mock()
        Response response = Mock()
        CookieJar cookieJar = new CookieJar(request, response)

        when:
        cookieJar.add('name2', 'value2')

        then:
        1 * response.cookie('name2', 'value2')
    }

    def "can remove cookie that is present"() {
        given:
        Request request = Mock()
        Response response = Mock()
        CookieJar cookieJar = new CookieJar(request, response)
        request.cookie('name3') >> 'value3'

        when:
        cookieJar.remove('name3')

        then:
        1 * response.removeCookie('name3')
    }

    def "will not remove cookie that isn't present"() {
        given:
        Request request = Mock()
        Response response = Mock()
        CookieJar cookieJar = new CookieJar(request, response)

        when:
        cookieJar.remove('name4')

        then:
        0 * response.removeCookie('name4')
    }
}
