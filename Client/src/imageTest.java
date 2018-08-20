import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by Administrator on 2018/4/18.
 */
public class imageTest {
    public static void main(String[] args) throws Exception{

        /*初始化*/
        URL url = new java.net.URL("http://localhost:8080/ADSer/ServletHandle");
            /* URL url = new java.net.URL("http://localhost:8080/ServiceTest/MeeServlet");*/
        URLConnection con = url.openConnection();

        //    con.setRequestProperty("ContentType","text/html;charset=utf-8");
        con.setUseCaches(false);
        con.setDoOutput(true);
        con.setDoInput(true);
        //   con.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
        try {
            String srcPath1 = "C:\\Users\\Administrator\\Desktop\\1.jpg";
            FileInputStream fis = new FileInputStream(srcPath1);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] buffer = new byte[fis.available()];
            int count = 0;
            count = fis.read(buffer);
//            while ((count = fis.read(buffer)) >= 0) {
//                baos.write(buffer, 0, count);
//
//            }

            String picture = new BASE64Encoder().encodeBuffer(baos.toByteArray());
            System.out.println(picture);
            Document document = DocumentHelper.createDocument();
            Element root = document.addElement("Root");
            // root.addAttribute("version", "1.0");
            Element root2 = root.addElement("Root2");
            Element method = root2.addElement("Method");
            method.setText("test");
            Element title = root2.addElement("picture1");
            title.setText(picture);

            String xxx= document.asXML();

            StringWriter xmlWriter = new StringWriter();
            OutputFormat outputFormat = new OutputFormat();    // 设置xml输出格式
            outputFormat.setEncoding("utf-8");
            outputFormat.setIndent(false);
            outputFormat.setNewlines(true);
            outputFormat.setTrimText(true);
            XMLWriter output = new XMLWriter(xmlWriter, outputFormat);        // 保存xml
            output.write(xxx);
            output.close();
                        /*发送*/
            DataOutputStream dataOut = new DataOutputStream(con.getOutputStream());
           String test = "/9j/4AAQSkZJRgABAQEAYABgAAD/2wBDAAgGBgcGBQgHBwcJCQgKDBQNDAsLDBkSEw8UHRofHh0a\n" +
                   "HBwgJC4nICIsIxwcKDcpLDAxNDQ0Hyc5PTgyPC4zNDL/2wBDAQkJCQwLDBgNDRgyIRwhMjIyMjIy\n" +
                   "MjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjL/wgARCAEvAVkDASIA\n" +
                   "AhEBAxEB/8QAGgAAAgMBAQAAAAAAAAAAAAAAAAEDBAUCBv/EABkBAAMBAQEAAAAAAAAAAAAAAAAB\n" +
                   "AgMEBf/aAAwDAQACEAMQAAABrbuJ3z92vl8nX5aYgIxZ69NjAct5d6Jwn2R0bjQr4u1tlm63n9vT\n" +
                   "OyD4u3O0b8MxKLvmlTR1W9bH0M7fWsCpQY+/y581X1KDiJpi6647H3LH2N9TWGBJz5vuiD0fEK92\n" +
                   "DoxwNvE9D5fuimrPKTVrXerzhOIMabRzu3ira2JttYe3ielal1Wed6cOZpYUcux1SamaKKNTZh5j\n" +
                   "vcEq2YgOPMeqymsvvZsC88ehpjy71PRqezguLPM0XmezHLFY9HyO6uhap+a0bFfg9DiKydvnzyVB\n" +
                   "zoS0pZvG1rvGkU+eM7bmrewwfTMnYcPoQ4G/kaZ1prODHDtZa2pz5q26Z6jSRTEA4+uABANqYdGD\n" +
                   "1uBpjSOFpGtXt0+HvUsa7OGRR15rRiyNNkrg4RYFtVOLrXMmNPRR4Feb9UY3KNteftM1hAMUISde\n" +
                   "VlD0tfChRYbSYcgNSThQ50uwzDcnZmaXYIaGR17gLDqz8KI47HG2NTrvqNbedYsq8m1e1Gu7TU0U\n" +
                   "roOhzooKJeAzbFpACQdR9oKcOlSRmdXu06kWgwydG3IwaTTaAYgGIBvlgABgsd8XPS1y4Jb5HVmS\n" +
                   "X0GbPaABMBCBggGgBNAAAkwFHKgE0AAACAQAwAAAYIGR5YtWvhJxM+Fwdt7cwLu+OsvB+grXcFHp\n" +
                   "zyLB0sVdQbtJ5F81q15SLXi9dP5yPzPS9H1h7nZmk0ugp2/MLq07Hj2uj2y8d10+V7Qq2saASYAA\n" +
                   "AAABF3nCpV9OJ50SaIXYLz/QmtUL1zZq2K1xu49SvPPf3a1jemLnR9Zdylpy4+r5D0KrRzKNiOjr\n" +
                   "0/n/AEm/ngGHoQ+O9r5rHvk8j6CltwX8XutrPpvTea9LrmIOXYAABA064oYBkgmJMAz0zg7ldpW7\n" +
                   "mTivd9Dix7vGhxmk6nfRcnCYEFivU+Vq7Wdtl5nj0E8Gr6GvKptFbvPq78/t4dGJBv8An6WFoWJ+\n" +
                   "rl3/AEflbca76x5eXfUM1BprHoBv1M3RJYClMBibDOTXB2lisrnVnzJOjC7LE9M5ua5BYIu4XfDo\n" +
                   "t6NfiGzubmC40VF1Ona6rsl5hbLNfLrj574e67RznowTE0hN8tDsVxLZK1qIQAgANapoE6+Vpeu8\n" +
                   "9jpRW5JawudLkVd6DMna5Jz6yrvFVnLR7byote0Hn9O53J1VsDPNW9yvq/Ox6tKrIrSZSs9DExMa\n" +
                   "EHSQjoQKTXw7srQBzCGw2ADSPzHq0HkV62sjMxfbjPFS+j6zeba0m8/P5Pr6CWE90SxIfRAYF/QE\n" +
                   "LtCTqWqFViK7z0a1u5WzjrjgUnPDmWCQ+ueh9C6DlpU9qbC25jpppPX8le5ureeZT1jTp+btYb+l\n" +
                   "t+W1NcdUoRPPSMeao1c7Qz2QPkzx6OWmxAdHLGYt6PfWkXVpdLm9GppqSOIF0DBg02IOkxviUbrX\n" +
                   "6kNL0yheaoAcXYJgUerSjbruCWuSx3DYqS/QubvQyLFbXkGUqi5X8rBv0+6fk/V8+bRXzUXoPPaW\n" +
                   "+0tny+o+f0lO5mRtg5+hQENcoK0Uetdlmu3Zt5ckmm+XmddzyxSXSiqozn25H2yKO3x0c9bqvc5c\n" +
                   "7rDbpABEE9nr4KWPLpbGPqQVysv11C/myr3xHQr9N3nV04diMNGJtdHkKF+kTzTvU6fZTl0urasU\n" +
                   "Wuw5HraGdrcrAIpDAqifPsd8DVqzk875c2Izn2vlSfSJCLjTCdwG/Lo1a3TLBXklScuubCZXW5a2\n" +
                   "Gq9HnYmjl03692DXzoCTiYXPUFNddUdKtVJJWRR2OAs7uVq81ocUEqrqoTRy9RQvQXPMUnGsFmr2\n" +
                   "K3xA757MsE5nFHLFTo2e5KnmZhXNScOmfh+ZjrvWsAz32ocsFtPL2deMIuFzz98dUqlnuto60tuQ\n" +
                   "ebDNZosalTvmrnhFc/QhnQLh9Ar2I6UbONc5OuJb5q1lcuZpIpiK9O7zd9cnbivJarVpwziemPE3\n" +
                   "lzenhHoe4182emja896BWNOaCHR608/Lms83EcRYZxQsNkde1eTnqdRxk3yOGJDmA4fRQADHtxvv\n" +
                   "jrTBp8gpYpWCaYpolphLB1NPc+4Y86ni4HmIGixX7V2AM9lySXFSK7DpHHMsA0KYK2h1Wiqq5NOT\n" +
                   "o4Y+jhj/AP/EACwQAAIBAwMDAwQDAQEBAAAAAAECAwAEERASExQgIQUwMSIjMzQVMkEkQFD/2gAI\n" +
                   "AQEAAQUCHgi/ejfyGmZnOv1PL89kce7syDU/4VuHWkbemhOTHbeA2BmtxFLJnS4/LpJEr1JG0dHz\n" +
                   "3jTJ0HazAV6ed1230zaRpvanbYqOHE89AlTP+CoPw1sL1DbrFTvuaP4H3aV0Lt4aJ9wn/LqRmp4e\n" +
                   "OvnsxQ0WOtq90zMiGQsLEbTP4kr/AFF2rpJhJLdYyZfy3H4Kg/DHGXpQFEpxHGN7RPHvMhLb12lQ\n" +
                   "Vgb7rnMmn+0y7lwVNcbGijLQ0jXA0x2AipHCKPra2GBOPCeVhTWRSyxxLGJ4dlfJuPwVZxboR40m\n" +
                   "GYUkKMjgB3K1Ad1CVVjt/wAndLAXmSFE1eAGgp390QzNfyCGQ/8AR6bHbEUPpormlTDcmKV86KI+\n" +
                   "CG4drgvGrosYqf8ABVl+npIMr6g4glhPNZiSOOo1WOTNKvFb+3JHg9jaReJOGP8AkrjhEQkATtEl\n" +
                   "OFmsraK1W2NpFPUnp0gpxMlRWMslQx8MWknheGM+p3Kx8RyKWOR6gtNlTvuftz2xoZCYkaKW0kj7\n" +
                   "D80PJH1HORscjOjAp2WgK6M4T2d41lk41/32MZqO2JpVCDRo0eukg7ASJoZcSWw+y+7p1ildt3IZ\n" +
                   "cpSgs0dmoqRFSEbxUt3JGt5LIYBcEObrwbpsRzs0msn9eV90dxPgXv1rcc9FcHVY2eumc10lC0Wl\n" +
                   "gjWgAO9vjRkD1xRUsMYr+PUq1rtriQ1FZl2hgEI06aKujhrooDXSRAC0iFdFBlIEj7GUOGtYWBtI\n" +
                   "VrpoqWCNDRFYJqOD3T5rGjf1MrFZS8MMk0oCXDGQXLxtFPySe/I2SI2NcNcNcC0qqv8A4VjMjCxQ\n" +
                   "V0x2dGMi389Gu+KARH31UD/wswWupi7LZMJ/8RnVA90TRJY0TmlbFfNLjZ7JIANyoKurgSBmrOe2\n" +
                   "ecQ11yVHcRyF3WNEkWRfbdtokDse1PiJ8G4t+Cf06Rmip3CKTmrdNq63P5ZLhYm6yOl9Ut0VvVIz\n" +
                   "UU+W7LqTMvOtdQoqS85ahuSrwzLMPZJwGbcaKA0YyOxPmp4uqtwDCM5FxJukhXkk7Lr5hk4/VYrM\n" +
                   "LbQrClX7XIgh/DHkR6SErHL/AE9NnxYFst61hbyD+9p+z7MjZPeD5pHZadjGYpzCM1BHxx6E4rOa\n" +
                   "uPx3blLmf1SJr2e5tLn1F7u1gsbJgqa/Au33x2F1ZQ2du3pxT1C8F5dWx+5Z/sexI+PaXyJG4GbE\n" +
                   "0OatfMnKtbxW7Vwzxy2qXU7emFWf6JM1aqWt0YgZ0Y4F5GFpfSmkS49MmtoM1Zn71l+fuyBTSePa\n" +
                   "h/tdct3fxwPbW88LF0UIoHbLKYLS3spWq7s0kvP46LEPpUUpijEUWm6i2au+W7v5bNl9Pb00JXQp\n" +
                   "UNsIni3I3US0Ltq6wV1iV1iUbxqZ2elcrQ8j2Y/neeZneTQDGmdc0clUd404kajaRmoouIdhrzzM\n" +
                   "zvUpAj2DPtRvtPsHwBMgqV+OGOT7olXDSha3jPItA51afbJyimnxHyjbLPsG/FBwTUjiNeXEXKA8\n" +
                   "tyQ/OZKR1MQeMlfMGQIvYhfcO0xo1PaowkRlrbJLUsTOpDhNrIAM0wmYokiCFSBoySGYwuXMLbY4\n" +
                   "HFTxTyqYJWkhVt9XA3RtazMAkoeaKVSNxr+sSnARcRcj7l8J3q21s5HdPGJIyUR8UbfBkZEER3MG\n" +
                   "JqOTe2jyBF5gG5/+bk2jnUzKdxDA9makmWKpJVeTcOImPZ9JQHPtwP3t5S5SSSpN6sFdFdJeadJj\n" +
                   "TNyNFLtaJjJWCKnUGtjlJeR4ZBuLg0E8Dlc8/lfK6SyBQuxaTZxP9Y3RZHxWazW41ms9o8VE+9e8\n" +
                   "qGCW8aNo1uMrABq8efbuH7SfcR9jAhl0trjHZJcJHXWUk4bsyBQIOkow/sE4DEk6n3CNLeTB1juX\n" +
                   "SutWpbpnHOaRt6hsVHOQOpzXUk1nNR/kqY/X7Ex8dLNXSzV0k1G1mx0stMpRvbZa/wBSQNHu7ZE+\n" +
                   "qPxoDRbAAwKgXNE7QTk6EgDqIc/OuaPmp7iO3rr4ahuY5pMLVxKsFXv7mrOIwJHkoPM03JMtLOpb\n" +
                   "X5pYAO8jI8BAaWo/qOlufqmfcaIwOpQCaZ5m4nK2c5jk0c6X+KzVj+wR5vRmr39zRjtUqZRyla3C\n" +
                   "Nf2J405prd22UKVdvsv8HzX9iO1y27AUSjcYbdXqVQIFh+8PgnGhq/rFWf7FSRrLV5+5pPlqhxbN\n" +
                   "DFwL8qGENuC5qNsWyNvRFx7EYBlv5BDJG3LY52VbjuRY+CG4drhoxyvAynFw1RQ40Y6J5k9QkWCS\n" +
                   "1HUW0cCxP9NZFXf7mgYC9/JcXTM86Ks1+0RKSP8A9hQXNzYeYPYjOJeGP+SueERMoakOD2ZFMBLZ\n" +
                   "W8dstsWG/UnA0Q4l4Y/5K/MaWkR5IyvbJGOjnHHAniwA6OZZsQW3iOzbZN6afZwdlZ86BiKDit2n\n" +
                   "+jzQOoOm7OgBJz4uJRIyoz1a7lIBNOCrlWTUuXjtgZbhnPHcycrTQKkN39E0ibbS2jEc+hetxrJ7\n" +
                   "N4SvIiiIaTlLWc5MdSYQKxLh8yVkiWGb67b6VaYrFNI0al/uLM3LG7Nb0pzcKQYcLNIu0T2seyeB\n" +
                   "ZNq74RxEOQyioSgdJOMxxhLV4SkU8vIyobh8vJFZkvozZ79iqeFDRiARoEYcIdunirp0pFC6MquO\n" +
                   "GOulipbdARbxVwQ1wpQiQEnAc8jbBWwVsFbFqNV5OFKMK4ESg0YwLaRQtm8m5GBu5bSIAiXbGQI/\n" +
                   "T7Vdlu7ew8kiI0giUuiFeMSK/wBSzBirZX/a3GopgKD/AHI5g0ETMzGQtNHKHeOTfHdNiPuVty/5\n" +
                   "pI2YlYORB9qE8VsJSsKQAW6hpWY4HsYIYKyFIjGEiMdGPK8IwYiaAxo8W8iHayxFaS2xFHCIhJGZ\n" +
                   "a2PlbdtrWbyMPTxXQR10cFdFDRsYq6BKS3CLwrXEtEcjiENKVy9y9R1PEkQkmDiJPLnLf+RDTnAA\n" +
                   "3UEA03Ct51zilOezaz0hEdbmCujFYo12rKFZYuSoowzscL259sd3+7xW896/Oh/rkskagDeORm5a\n" +
                   "2O9ZySTPpMfHd//EACkRAAEDAwQCAQQDAQAAAAAAAAEAAhEDEBIEICExEyJBFDAyUTNAYXH/2gAI\n" +
                   "AQMBAT8B1w9Q5TbTtzf/AMVan43ltuk6o1rckK3kY5aV7iYNj69qZWUKZFi1YqFCi1UZMIt5DlEL\n" +
                   "TUgxvS1zOA9BHpGk5nvCpGWvK0n5qFXdDhKz4WfHKoGWXIWKxTrVX4sJs3IHJVdYMIZ2g9wkKVP6\n" +
                   "UPDeE+o5rS0thaSm4OyIs8H4WocWOCY11XhoTW4iBueLap3AC7WKxWCAlBsfcm8KvVzdwpWKwWH9\n" +
                   "ISmCT/TJhZINAEBOYOwn0MWZg7NTX8LMoVLU6o+2MhAlrfbkrT1xWB/y2koio/26CqUtLHq5OJad\n" +
                   "xUXKFXH1KlC2rblRcFpf4W20IHsbaSpi1wizzDVRMt+y7pZFxQU2d1ymvFNnPSZqqb3YhUNMKRLv\n" +
                   "k2NRzPxUw3JyqahhaQFpmkM5UXO1x+E4D4vNonhCm0GQNkSm02M6H2QSFkD2uFATpClCVJUkKVKl\n" +
                   "Ded5iOFgpLVmsllZvdy5SpU3NnM/SwK8GIkpzP0hyi4gwn9bWcKUT9jE3D5biU94x4TBwntnlVHI\n" +
                   "GUKQ+SnsxKY3IqqYCbUJMJxgXhRcC5XmDjgmsJMGx6USv9C5Ke6VTbAVfpMp8g3Cm7RsKbSDSoR4\n" +
                   "RrD4Uwi7iFKpNyNqen8zoVSgxlMieU4fNhsHSJheQWcJWJTpbyUXz2isoCNRp+Fk39JrYVCh5ZX0\n" +
                   "dUdOX0dSSZVegaZgo9obJgImbkSnAwnPkokG2QjlB1NU/YmFytPWpU6cEo62kvraS1VWnVaMe0RO\n" +
                   "wBPdJ21amXA3UnNa1GqFmTZps52w+rdn/8QAKhEAAQMEAQMFAAEFAAAAAAAAAQACEQMQEiExBCAi\n" +
                   "EzAyQVEjM0BhcYH/2gAIAQIBAT8BoHcXruNOkXKm7Js2G0ym5zsUaXpvaupY0NkWfUgaRBKEtQM2\n" +
                   "lT2QmGHCwpiJldU/ILpXeMWCFRr/AAlVRDmBdX8LOG7Qm8dkqU21MZOAs7HHFU6DnO8+FiFCA3tE\n" +
                   "0y5Mptc4ODpXVVGluItTidp2zIUd7TbphslcLJZLJEwi73I7KLMG7tO4QcsvahR3yp/tZPKY9NqS\n" +
                   "7E2p1szEWY3IwnMpj7TnV3v8NBYOaPKxKCbSa5vO0QQYPstTwSJCqVcmwFTZg2LUvmFU+RtVPAtV\n" +
                   "qBsWo/1Aur+fss5RptDEylDrt5TqeTvFGi4CSnPy1ZtJrz5fS9OTDVR6Wo14cV1cOqaWJUFQo7WA\n" +
                   "8hNLvu2RWRQLkHvG16zyIKm4T6rnaJWQ9gtBWLm8KXQsnLI2ErayPKlByG0IhGFiO09+1gtjSyWS\n" +
                   "mzOVioUqVKlT2Mqxoo1Wo1SUyp+r1mcIOTuO2mIEqUT3SuUSBdxhEqm2AmHajIwEaTkOlEeRVSng\n" +
                   "YQEpoEogIR93DbOb+WLvy4EmE7pSGZymt3YJhgofw8okPOUqu/M6QCY0kwE4EOi7eVCmbP5jsHMp\n" +
                   "1cuFgZWKaY2iZsBY1MAmvJddq/wv9rn/AKqnyULCzXQs2/iBafpasOx78V6gP0vUCY6drhNXy4WW\n" +
                   "pK4Ttu7GmE1zZQgLVpuNJ8uKxKxKp+PKLghA5X3AQjKCi4gSU0drGxcXc78UE8qLG0RtTs6QnGDp\n" +
                   "HXCnI9n/xAA8EAABAwMBBQQIBQQCAgMAAAABAAIRAxIhMRAiMkFREyAwYQQjQlJxgZGxM0ByodFi\n" +
                   "weHwFFNDUHOy8f/aAAgBAQAGPwKVljSsNAUuMnuNps17snTuYKcuqu7l1TA6KGiBtzsO3zWdOvha\n" +
                   "+AXpw2+WySpCsb8ysYTtjdkNUnLuuxztYC0DY1KDQzB5lELzCPcghXDh7s7d5ad6QNs7Y2nsys8X\n" +
                   "RO+KdsavJQESomFY1uvMosp02kDXCvNMa+yUXMdMag6qOqd8e6QeaLTy2cJWW7Z8CSp7l22AYWPq\n" +
                   "rmabHbGk6bXKQn1gI5BdrTMB+vxTqfvBENBuOCSp6DvyMDmtM9dstwVHfYD7wQFO2ebbAiSG3dpE\n" +
                   "hscllRs8lptqvfAg8UTGFTabC0vA/DCe2QIccLchO2M+f32gHS4fdBtOyebbBhXOsDu1i62MQrJL\n" +
                   "2Hi/wu17RpYPrsJPE7xO05HHf";
            System.out.println(count);
           dataOut.write(buffer,0,count);
          // dataOut.writeInt(xxx.length());

            //dataOut.writeUTF(xxx);
            dataOut.flush();
            dataOut.close();


                /*获取服务器端返回信息*/
            DataInputStream in =new DataInputStream(con.getInputStream());
            // int str = in.readInt();
            String stringValue = in.readUTF();
            in.close();
            System.out.println(stringValue);
        } catch (Exception exception) {
            exception.printStackTrace();
        }



    }
}
