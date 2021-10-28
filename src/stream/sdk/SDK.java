package stream.sdk;

import java.io.*;
import java.net.*;
import java.security.*;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;
import javax.net.ssl.HttpsURLConnection;
import javax.xml.bind.*;

public class SDK {

    private final URL url;
    private String agent;
    private final ThreadLocal<String> language =
            new ThreadLocal<>();
    private String key;
    private boolean debug =
            false;

    public SDK(String url) throws MalformedURLException {
        this.url = new URL(url);
        this.language.set("az");
    }

    public void setDebug(boolean debug) {
        this.debug = debug;
    }

    public void setAuthenticationData(
            String agent,
            String key
            ) {
        if (agent == null) {
            throw new IllegalArgumentException("Agent should not be null");
        }
        if (key == null) {
            throw new IllegalArgumentException("Key should not be null");
        }
        this.agent = agent;
        this.key = key;
    }

    public void setLanguage(String code) {
        this.language.set(code);
    }

    public List<Category> getCategoryList()
            throws
            stream.sdk.Error,
            IOException {
        try {
            if (agent == null) {
                throw new IllegalStateException(
                        "Not authenticated. Call 'SetAuthenticationData(...)' first"
                        );
            }
            SHA1HMACSignature signature =
                    new SHA1HMACSignature(key);
            signature.setMethod("GET");
            signature.setURI(String.format("%s/categories", url.getPath()));
            HttpsURLConnection connection = (HttpsURLConnection)
                    new URL(String.format("%s/categories", url)).openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty(
                    "X-Signature",
                    signature.calculate()
                    );
            connection.setRequestProperty(
                    "X-Agent",
                    agent
                    );
            connection.setRequestProperty(
                    "X-Format",
                    "XML"
                    );
            connection.setRequestProperty(
                    "X-Language",
                    this.language.get()
                    );
            Scanner s = new Scanner(
                    connection.getInputStream(),
                    "utf-8"
                    ).useDelimiter("\\A");
            String data = s.hasNext() ? s.next() : "";
            if(debug) System.out.println(data);
            JAXBContext jaxb =
                    JAXBContext.newInstance(Response[].class);
            Unmarshaller unmarshaller =
                    jaxb.createUnmarshaller();
            Response response = (Response)
                    unmarshaller.unmarshal(new StringReader(data.trim()));
            if (!response.getCode().equals("0")) {
                throw new stream.sdk.Error(
                        response.getCode(),
                        response.getMessage()
                        );
            }
            return response.getCategoryList();
        } catch ( JAXBException
                | MalformedURLException
                | NoSuchAlgorithmException
                | InvalidKeyException e
                ) {
            throw new IOException(e);
        }
    }

    public List<Service> getServiceList(
            int categoryId,
            String keyword,
            int offset,
            int limit
            )
            throws
            Error,
            IOException {
        try {
            TreeMap<String, Object> map = new TreeMap();
            map.put("category", categoryId);
            if (keyword != null) {
                map.put("keyword", keyword);
            }
            map.put("offset", offset);
            map.put("limit", limit);
            String values = map.entrySet().stream().map(
                    e -> String.format("%s=%s", e.getKey(), urlEncode(e.getValue().toString()))
                    ).collect(Collectors.joining("&"));
            SHA1HMACSignature signature = new SHA1HMACSignature(key);
            signature.setMethod("GET");
            signature.setURI(String.format("%s/services", url.getPath()));
            signature.addParameter(map.get("category"));
            signature.addParameter(map.get("keyword"));
            signature.addParameter(map.get("offset"));
            signature.addParameter(map.get("limit"));
            HttpsURLConnection connection = (HttpsURLConnection)
                    new URL(String.format("%s/services?%s", url, values)).openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty(
                    "X-Signature",
                    signature.calculate()
                    );
            connection.setRequestProperty(
                    "X-Agent",
                    agent
                    );
            connection.setRequestProperty(
                    "X-Format",
                    "XML"
                    );
            connection.setRequestProperty(
                    "X-Language",
                    this.language.get()
                    );
            Scanner s = new Scanner(
                    connection.getInputStream(),
                    "utf-8"
                    ).useDelimiter("\\A");
            String data = s.hasNext() ? s.next() : "";
            if(debug) System.out.println(data);
            JAXBContext jaxb =
                    JAXBContext.newInstance(Response.class);
            Unmarshaller unmarshaller = jaxb.createUnmarshaller();
            Response response = (Response)
                    unmarshaller.unmarshal(new StringReader(data));
            if (!response.getCode().equals("0")) {
                throw new Error(response.getCode(), response.getMessage());
            }
            return response.getServiceList();
        } catch ( InvalidKeyException
                | NoSuchAlgorithmException
                | JAXBException e
                ) {
            throw new IOException(e);
        }
    }

    public Service getService(
            String alias
            )
            throws
            Error,
            IOException {
        try {
            SHA1HMACSignature signature = new SHA1HMACSignature(key);
            signature.setMethod("GET");
            signature.setURI(String.format("%s/services/%s", url.getPath(), alias));
            HttpsURLConnection connection = (HttpsURLConnection)
                    new URL(String.format("%s/services/%s", url, alias)).openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty(
                    "X-Signature",
                    signature.calculate()
                    );
            connection.setRequestProperty(
                    "X-Agent",
                    agent
                    );
            connection.setRequestProperty(
                    "X-Format",
                    "XML"
                    );
            connection.setRequestProperty(
                    "X-Language",
                    this.language.get()
                    );
            Scanner s = new Scanner(
                    connection.getInputStream(),
                    "utf-8"
                    ).useDelimiter("\\A");
            String data = s.hasNext() ? s.next() : "";
            if(debug) System.out.println(data);
            JAXBContext jaxb =
                    JAXBContext.newInstance(Response.class);
            Unmarshaller unmarshaller = jaxb.createUnmarshaller();
            Response response = (Response)
                    unmarshaller.unmarshal(new StringReader(data));
            if (!response.getCode().equals("0")) {
                throw new Error(response.getCode(), response.getMessage());
            }
            return response.getServiceList().get(0);
        } catch ( JAXBException
                | MalformedURLException
                | NoSuchAlgorithmException
                | InvalidKeyException e
                ) {
            throw new IOException(e);
        }
    }

    public List<Option> getOptionList(
            int service,
            Map<String, Object> detailList
            )
            throws
            Error, 
            IOException {
        try {
            SHA1HMACSignature signature = new SHA1HMACSignature(key);
            signature.setMethod("GET");
            signature.setURI(String.format("%s/options", url.getPath()));
            signature.addParameter(service);
            Map<String, Object> map = new TreeMap<>();
            if (detailList != null) {
                detailList.entrySet().stream().map(e -> {
                    signature.addParameter(e.getValue());
                    return e;
                }).forEachOrdered(e -> {
                    map.put(e.getKey(), e.getValue());
                });
            }
            String values = map.entrySet().stream().map(
                    e -> String.format("(%s)=%s", e.getKey(), urlEncode(e.getValue().toString()))
                    ).collect(Collectors.joining("&"));
            HttpsURLConnection connection = (HttpsURLConnection) new URL(
                    String.format("%s/options?service=%s&%s", url, service, values)
                    ).openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty(
                    "X-Signature",
                    signature.calculate()
                    );
            connection.setRequestProperty(
                    "X-Agent",
                    agent
                    );
            connection.setRequestProperty(
                    "X-Format",
                    "XML"
                    );
            connection.setRequestProperty(
                    "X-Language",
                    this.language.get()
                    );
            connection.setRequestProperty(
                    "Content-Type",
                    "application/x-www-form-urlencoded; charset=utf-8"
                    );
            Scanner s = new Scanner(
                    connection.getInputStream(),
                    "utf-8"
                    ).useDelimiter("\\A");
            String data = s.hasNext() ? s.next() : "";
            if(debug) System.out.println(data);
            JAXBContext jaxb = JAXBContext.newInstance(Response.class);
            Unmarshaller unmarshaller = jaxb.createUnmarshaller();
            Response response = (Response) unmarshaller.unmarshal(new StringReader(data));
            if (!response.getCode().equals("0")) {
                throw new Error(response.getCode(), response.getMessage());
            }
            return response.getOptionList();
        } catch ( InvalidKeyException
                | NoSuchAlgorithmException
                | JAXBException e
                ) {
            throw new IOException(e);
        }
    }

    public String register(
            String service,
            int amount,
            int fee,
            int currency,
            String transactionId,
            Date date,
            Channel channel,
            Source source,
            String description,
            String tag,
            Map<String, Object> detailList
            )
            throws
            IOException,
            Error {
        try {
            Map<String, Object> hash = new TreeMap<>();
            if (detailList != null) {
                detailList.entrySet().forEach(e -> {
                    hash.put(e.getKey(), e.getValue());
                });
            }
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            SHA1HMACSignature signature = new SHA1HMACSignature(key);
            signature.setMethod("POST");
            signature.setURI(String.format("%s/payments", url.getPath()));
            signature.addParameter(service);
            signature.addParameter(amount);
            signature.addParameter(fee);
            signature.addParameter(currency);
            signature.addParameter(transactionId);
            signature.addParameter(sdf.format(date));
            signature.addParameter(channel);
            signature.addParameter(source);
            signature.addParameter(description);
            signature.addParameter(tag);
            hash.entrySet().forEach(m -> {
                signature.addParameter(m.getValue());
            });
            HttpsURLConnection connection = (HttpsURLConnection)
                    new URL(String.format("%s/payments", url)).openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty(
                    "X-Signature",
                    signature.calculate()
                    );
            connection.setRequestProperty(
                    "X-Agent",
                    agent
                    );
            connection.setRequestProperty(
                    "X-Format",
                    "XML"
                    );
            connection.setRequestProperty(
                    "X-Language",
                    this.language.get()
                    );
            connection.setRequestProperty(
                    "Content-Type",
                    "application/x-www-form-urlencoded; charset=utf-8"
                    );
            Map<String, Object> map =
                    new TreeMap<>();
            map.put("service", service);
            map.put("amount", amount);
            map.put("fee", fee);
            map.put("currency", currency);
            map.put("transaction-id", transactionId);
            map.put("date", sdf.format(date));
            map.put("channel", channel);
            map.put("source", source);
            map.put("description", description);
            map.put("tag", tag);
            hash.entrySet().forEach(m -> {
                map.put(String.format("(%s)", m.getKey()), m.getValue());
            });
            String values = map.entrySet().stream().filter(e -> e.getValue() != null).map(
                    e -> String.format("%s=%s", e.getKey(), urlEncode(e.getValue().toString()))
                    ).collect(Collectors.joining("&"));
            if(debug) System.out.println(values);
            byte[] array = values.getBytes("UTF-8");
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            connection.setRequestProperty("Content-Length", String.valueOf(array.length));
            connection.setDoOutput(true);
            try (DataOutputStream stream = new DataOutputStream(connection.getOutputStream())) {
                stream.write(array);
            }
            Scanner s = new Scanner(
                    connection.getInputStream(),
                    "utf-8"
                    ).useDelimiter("\\A");
            String data = s.hasNext() ? s.next() : "";
            if(debug) System.out.println(data);
            JAXBContext jaxb = JAXBContext.newInstance(
                    Response.class
                    );
            Unmarshaller jaxbUnmarshaller = jaxb.createUnmarshaller();
            Response response = (Response) jaxbUnmarshaller.unmarshal(
                    new StringReader(data)
                    );
            if (!response.getCode().equals("0")) {
                throw new Error(response.getCode(), response.getMessage());
            }
            return response.getReference();
        } catch ( InvalidKeyException
                | NoSuchAlgorithmException
                | JAXBException e
                ) {
            throw new IOException(e);
        }
    }

    public Payment pay(
            String reference
            )
            throws
            Error,
            IOException {
        try {
            SHA1HMACSignature signature =
                    new SHA1HMACSignature(key);
            signature.setMethod("POST");
            signature.setURI(String.format("%s/payments/%s", url.getPath(), reference));
            HttpsURLConnection connection = (HttpsURLConnection)
                    new URL(String.format("%s/payments/%s", url, reference)).openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty(
                    "X-Signature",
                    signature.calculate()
                    );
            connection.setRequestProperty(
                    "X-Agent",
                    agent
                    );
            connection.setRequestProperty(
                    "X-Format",
                    "XML"
                    );
            connection.setRequestProperty(
                    "X-Language",
                    this.language.get()
                    );
            Scanner s = new Scanner(
                    connection.getInputStream(),
                    "utf-8"
                    ).useDelimiter("\\A");
            String data = s.hasNext() ? s.next() : "";
            if(debug) System.out.println(data);
            JAXBContext jaxb = JAXBContext.newInstance(
                    Response.class
                    );
            Unmarshaller jaxbUnmarshaller = jaxb.createUnmarshaller();
            Response response = (Response) jaxbUnmarshaller.unmarshal(
                    new StringReader(data)
                    );
            if (!response.getCode().equals("0")) {
                throw new Error(response.getCode(), response.getMessage());
            }
            return response.getPaymentList().get(0);
        } catch ( InvalidKeyException
                | NoSuchAlgorithmException
                | JAXBException e
                ) {
            throw new IOException(e);
        }
    }

    public Payment getPayment(
            String alias
            )
            throws
            IOException,
            Error {
        try {
            SHA1HMACSignature signature =
                    new SHA1HMACSignature(key);
            signature.setMethod("GET");
            signature.setURI(String.format("%s/payments/%s", url.getPath(), alias));
            HttpsURLConnection connection = (HttpsURLConnection)
                    new URL(String.format("%s/payments/%s", url, alias)).openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty(
                    "X-Signature",
                    signature.calculate()
                    );
            connection.setRequestProperty(
                    "X-Agent",
                    agent
                    );
            connection.setRequestProperty(
                    "X-Format",
                    "XML"
                    );
            connection.setRequestProperty(
                    "X-Language",
                    this.language.get()
                    );
            Scanner s = new Scanner(
                    connection.getInputStream(),
                    "utf-8"
                    ).useDelimiter("\\A");
            String data = s.hasNext() ? s.next() : "";
            if(debug) System.out.println(data);
            JAXBContext jaxb = JAXBContext.newInstance(Response.class);
            Unmarshaller jaxbUnmarshaller = jaxb.createUnmarshaller();
            Response response = (Response) jaxbUnmarshaller.unmarshal(new StringReader(data));
            if (!response.getCode().equals("0")) {
                throw new Error(response.getCode(), response.getMessage());
            }
            return response.getPaymentList().get(0);
        } catch ( InvalidKeyException
                | NoSuchAlgorithmException
                | JAXBException e
                ) {
            throw new IOException(e);
        }
    }

    public List<Payment> getPaymentList(
            String tag,
            Date from,
            Date to,
            int offset,
            int limit
            )
            throws
            IOException,
            Error {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(
                    "yyyy-MM-dd'T'HH:mm:ss"
                    );
            LinkedHashMap<String, Object> hash =
                    new LinkedHashMap<>();
            hash.put("tag", tag);
            hash.put("from", from != null ? sdf.format(from) : null);
            hash.put("to", to != null ? sdf.format(to) : null);
            hash.put("offset", offset);
            hash.put("limit", limit);
            SHA1HMACSignature signature =
                    new SHA1HMACSignature(key);
            signature.setMethod("GET");
            signature.setURI(String.format("%s/payments", url.getPath()));
            signature.addParameter(tag);
            signature.addParameter(hash.get("from"));
            signature.addParameter(hash.get("to"));
            signature.addParameter(offset);
            signature.addParameter(limit);

            String values = hash.entrySet().stream().filter(
                    e -> e.getValue() != null).map(
                    e -> String.format("%s=%s", e.getKey(), urlEncode(e.getValue().toString()))
                    ).collect(Collectors.joining("&"));
            if(debug) System.out.println(String.format("%s/payments?%s", url, values));
            HttpsURLConnection connection = (HttpsURLConnection) new URL(String.format("%s/payments?%s", url, values)).openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty(
                    "X-Signature",
                    signature.calculate()
                    );
            connection.setRequestProperty(
                    "X-Agent",
                    agent
                    );
            connection.setRequestProperty(
                    "X-Format",
                    "XML"
                    );
            connection.setRequestProperty(
                    "X-Language",
                    this.language.get()
                    );
            Scanner s = new Scanner(
                    connection.getInputStream(),
                    "utf-8"
                    ).useDelimiter("\\A");
            String data = s.hasNext() ? s.next() : "";
            if(debug) System.out.println(data);
            JAXBContext jaxb = JAXBContext.newInstance(Response.class);
            Unmarshaller jaxbUnmarshaller = jaxb.createUnmarshaller();
            Response response = (Response) jaxbUnmarshaller.unmarshal(new StringReader(data));
            if (!response.getCode().equals("0")) {
                throw new Error(response.getCode(), response.getMessage());
            }
            return response.getPaymentList();
        } catch ( InvalidKeyException
                | NoSuchAlgorithmException
                | JAXBException e
                ) {
            throw new IOException(e);
        }
    }

    private String urlEncode(String txt) {
        try {
            return URLEncoder.encode(txt, "ASCII");
        } catch (UnsupportedEncodingException e) {
            return null;
        }
    }
}