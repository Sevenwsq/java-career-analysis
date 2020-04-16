package cn.wsq.analysis.javacareer.utils;

import cn.wsq.analysis.javacareer.pojo.Position;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Silent
 * @date 2020/4/10 12:15:14
 * @description
 */
@Component
public class CrawUtils {

    public List<Position> crawLagou(int page) throws IOException {
        List<Position> positions = new LinkedList<>();
        String url = "https://www.lagou.com/zhaopin/Java/"+page+"/";
        Connection connection = Jsoup.connect(url);
        connection.header("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
        connection.header("Accept-Encoding","gzip, deflate, br");
        connection.header("Accept-Language","zh-CN,zh;q=0.8,zh-TW;q=0.7,zh-HK;q=0.5,en-US;q=0.3,en;q=0.2");
        connection.header("Cache-Control","max-age=0");
        connection.header("Connection","keep-alive");
        connection.header("Cookie","_ga=GA1.2.657581519.1540284659; LGUID=20181023165058-c29768e0-d6a0-11e8-80a4-5254005c3644; LG_LOGIN_USER_ID=075bc5ec617260d83c795bee56103d3fe09a5ba57fc330e82cb310e4caa793fd; user_trace_token=20191226172424-ed0310c4-6e0b-48a5-8457-4dc6144a843f; sensorsdata2015jssdkcross=%7B%22distinct_id%22%3A%2215039735%22%2C%22%24device_id%22%3A%22166a0a558c65b0-08d1044ca8746b-5e442e19-1327104-166a0a558c76c9%22%2C%22props%22%3A%7B%22%24latest_traffic_source_type%22%3A%22%E7%9B%B4%E6%8E%A5%E6%B5%81%E9%87%8F%22%2C%22%24latest_referrer%22%3A%22%22%2C%22%24latest_referrer_host%22%3A%22%22%2C%22%24latest_search_keyword%22%3A%22%E6%9C%AA%E5%8F%96%E5%88%B0%E5%80%BC_%E7%9B%B4%E6%8E%A5%E6%89%93%E5%BC%80%22%2C%22%24os%22%3A%22Windows%22%2C%22%24browser%22%3A%22Chrome%22%2C%22%24browser_version%22%3A%2279.0.3945.130%22%7D%2C%22first_id%22%3A%22166a0a558c65b0-08d1044ca8746b-5e442e19-1327104-166a0a558c76c9%22%7D; _gid=GA1.2.1752405510.1586687569; gate_login_token=3257e747550ffdf7b520d2e2e7dd7e3c1d71a0d0c24eb4dfa063c435f9e49411; showExpriedIndex=1; showExpriedCompanyHome=1; showExpriedMyPublish=1; hasDeliver=0; privacyPolicyPopup=false; index_location_city=%E5%85%A8%E5%9B%BD; JSESSIONID=ABAAAECABBJAAGICF12F5A53770631D798163574D0C2C9E; WEBTJ-ID=20200413123936-17171d5e9dd349-0a80fc4f8babe3-43564257-1327104-17171d5e9de674; _putrc=AC692625B9F41D9D123F89F2B170EADC; login=true; LGSID=20200413123939-c11b70ba-97ac-4cab-8a0a-d252945553ff; Hm_lvt_4233e74dff0ae5bd0a3d81c6ccf756e6=1586687581,1586689292,1586744220,1586752779; unick=%E6%8B%89%E5%8B%BE%E7%94%A8%E6%88%B76993; TG-TRACK-CODE=jobs_code; X_MIDDLE_TOKEN=79998b126655fea271b7c519e9a5fc19; _gat=1; SEARCH_ID=5e322468877543b1847e4b6b3386ff1c; X_HTTP_TOKEN=e31553f0b712777d98545768510ee290a29dfa2ac2; Hm_lpvt_4233e74dff0ae5bd0a3d81c6ccf756e6=1586754589; LGRID=20200413130949-a2636f08-b6a4-4e64-b52f-862f104061f3");
        connection.header("Host","www.lagou.com");
        connection.header("User-Agent","Mozilla/5.0 Gecko/20100101 Firefox/72.0");
        connection.ignoreHttpErrors(true);
        Connection.Response response = connection.method(Connection.Method.GET).execute();
        Document document = response.parse();
        Element bodyElement = document.body();
//        System.out.println(bodyElement);
        Elements itemConList = bodyElement.select(".item_con_list .con_list_item");
//        System.out.println(itemConList.text());
        for (Element itemCon : itemConList) {
            Position position = new Position();
            position.setJobTitle(itemCon.select("h3").text());
            position.setSalary(itemCon.select(".money").text());
            String address = itemCon.select("em").text();
            String[] addresses = address.split("·");
            if (addresses.length == 2) {
                position.setCity(addresses[0]);
                position.setDistrict(addresses[1]);
            } else {
                position.setCity(address);
            }

            position.setCompany(itemCon.select(".company_name").text());
            String companyLink = itemCon.select(".company_name a").attr("href");
            position.setCompanyLink(companyLink);
            String p_bot = itemCon.select(".p_bot").text();
            position.setExRequired(p_bot.substring(p_bot.indexOf("经"),p_bot.indexOf("/")-1));
            position.setEducation(p_bot.substring(p_bot.indexOf("/")+1).trim());
            String logo = itemCon.select(".list_item_top .com_logo a img").attr("src");
            position.setCompanyLogo(logo);
            Elements tags = itemCon.select(".list_item_bot .li_b_l span");
            StringBuilder s = new StringBuilder();
            for (Element tag : tags) {
                s.append(tag.text()).append(",");
            }
            position.setTags(s.toString());
            positions.add(position);
            System.out.println(position);
        }
        return positions;
    }
}
