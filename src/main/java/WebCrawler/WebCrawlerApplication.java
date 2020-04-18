package WebCrawler;




import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;


import java.net.URL;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class WebCrawlerApplication {
  private final Set<URL> links;
  private final long startTime;
/*this is a constructor for getting urls*/
  private WebCrawlerApplication(final URL startURL) throws IOException {
    this.links =new HashSet<URL>();//suspicious
    this.startTime = System.currentTimeMillis();
    crawl(initURLS(startURL));
  }

  private Set<URL> initURLS(final URL startURL)
  {
    return Collections.singleton(startURL);
  }
  /*crawl method performs the functionality of crawling or scrapping*/
  private void crawl(final Set<URL> urls) throws IOException {
    urls.removeAll(this.links);
    if(!urls.isEmpty()) {
      final Set<URL> newURLS = new HashSet<URL>();
        this.links.addAll(urls);
        for(final URL url : urls) {
          System.out.println("time = "
                  +(System.currentTimeMillis() - this.startTime)+ " connected to : " + url);
          final Document document = Jsoup.connect(url.toString()).get();
          final Elements linksOnPage = document.select("a[href]");
          for(final Element element : linksOnPage) {
            final String urlText = element.attr("abs:href");
            final URL discoveredURL = new URL(urlText);
            newURLS.add(discoveredURL);
          }
        }

      crawl(newURLS);
    }
  }

  public static void main(String[] args) throws IOException {
  final WebCrawlerApplication crawler =new WebCrawlerApplication(new URL("http://facebook.com"));

  }
}
