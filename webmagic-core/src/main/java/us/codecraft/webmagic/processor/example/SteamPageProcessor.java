package us.codecraft.webmagic.processor.example;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Selectable;

import java.util.List;

public class SteamPageProcessor implements PageProcessor {
    private Site site = Site.me()
            .setDomain("store.steampowered.com")
            .setSleepTime(3000)
            .addHeader("Accept-Language","zh-CN,zh;q=0.9,en;q=0.8")
            .setUserAgent("Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/67.0.3396.99 Safari/537.36");
    private final  String XPATH="//div[@id='search_result_container']/div[2]/a";
    private final String REGE = "https://store\\.steampowered\\.com(/search/\\?specials=1&(page=\\d+&)?os=win)?$";
    @Override
    public void process(Page page) {
        if(page.getUrl().regex(REGE).match())
        {
            page.addTargetRequests(page.getHtml().xpath("//div[@class='home_page_content special_offers']/h2//a")
                    .links()
                    .all());
        }
        else
        {
            System.out.println( page.getHtml().xpath("//div[@class='search_pagination']").links().regex(REGE).all());
           /* page.putField("name",page.getHtml().xpath(XPATH).xpath("//div[@class='col search_name ellipsis']/span/text()").all());
            page.putField("discount",page.getHtml().xpath(XPATH).xpath("//div[@class='col search_discount responsive_secondrow']/span/text()").all());
            page.putField("original_price",page.getHtml().xpath(XPATH).xpath("//div[@class='col search_price discounted responsive_secondrow']/text()").all());
            page.putField("current_price",page.getHtml().xpath(XPATH).xpath("//div[@class='col search_price discounted responsive_secondrow']//strike/text()").all());
            //将下一页的链接加入待抓取队列
            page.addTargetRequests(page.getHtml().xpath("//div[@class='search_pagination']").links().regex(REGE).all());*/
       }
    }

    @Override
    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {
        Spider.create(new SteamPageProcessor()).addUrl("https://store.steampowered.com").run();
    }
}
