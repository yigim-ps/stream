using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Net;
using System.Text;
using System.Threading;
using System.Web;
using System.Xml.Serialization;

namespace STREAM
{
    public class SDK
    {
        private string url;
        private string agent;
        private ThreadLocal<string> language =
            new ThreadLocal<string>();
        private String key;

        public SDK(string url)
        {
            this.url = url;
            this.language.Value = "az";
        }

        public void SetAuthenticationData(string agent, string key)
        {
            if(agent == null || key == null)
            {
                throw new ArgumentException(
                    "Arguments [agent] and [key] must not be null!"
                    );
            }
            this.agent = agent;
            this.key = key;
        }

        public void SetLanguage(string code)
        {
            this.language.Value = code;
        }

        public List<Category> GetCategoryList()
        {
            if (agent == null)
            {
                throw new InvalidOperationException(
                    "Not authenticated. Call 'SetAuthenticationData(...)' first"
                    );
            }
            SHA1HMACSignature signature = new SHA1HMACSignature(key);
            signature.Method = "GET";
            signature.Path = String.Format("{0}/categories", new Uri(url).AbsolutePath);
            signature.Clear();

            WebRequest request = WebRequest.Create(String.Format("{0}/categories", url));
            request.Method = "GET";
            request.Headers.Add("X-Agent", agent);
            request.Headers.Add("X-Signature", signature.Calculate());
            request.Headers.Add("X-Format", "XML");
            request.Headers.Add("X-Language", this.language.Value);

            WebResponse response = request.GetResponse();
            using (Stream stream = response.GetResponseStream())
            {
                StreamReader reader = new StreamReader(stream);
                XmlSerializer serializer =
                    new XmlSerializer(typeof(Response));
                Response r = (Response)serializer.Deserialize(reader);
                response.Close();
                if (r.Code != "0")
                {
                    throw new Error(r.Code, r.Message);
                }
                return r.CategoryList;
            }
        }

        public Service GetService(string alias)
        {
            if (agent == null)
            {
                throw new InvalidOperationException(
                    "Not authenticated. Call 'SetAuthenticationData(...)' first"
                    );
            }
            SHA1HMACSignature signature = new SHA1HMACSignature(key);
            signature.Method = "GET";
            signature.Path = String.Format("{0}/services/{1}", new Uri(url).AbsolutePath, alias);
            signature.Clear();
            WebRequest request = WebRequest.Create(
                String.Format("{0}/services/{1}", url, alias)
                );
            request.Method = "GET";
            request.Headers.Add("X-Agent", agent);
            request.Headers.Add("X-Signature", signature.Calculate());
            request.Headers.Add("X-Format", "XML");
            request.Headers.Add("X-Language", this.language.Value);

            WebResponse response = request.GetResponse();
            using (Stream stream = response.GetResponseStream())
            {
                StreamReader reader = new StreamReader(stream);
                XmlSerializer serializer =
                    new XmlSerializer(typeof(Response));
                Response r = (Response)serializer.Deserialize(reader);
                response.Close();
                if (r.Code != "0")
                {
                    throw new Error(r.Code, r.Message);
                }
                return r.ServiceList[0];
            }
        }

        public List<Service> GetServiceList(
            int? category = null,
            string keyword = null,
            int offset = 0,
            int limit = 100
            )
        {
            if (key == null)
            {
                throw new InvalidOperationException(
                    "Not authenticated. Call 'SetAuthenticationData(...)' first"
                    );
            }
            Dictionary<string, object> map =
                new Dictionary<string, object>();
            map.Add("category", category);
            map.Add("keyword", keyword);
            map.Add("offset", offset);
            map.Add("limit", limit);

            SHA1HMACSignature signature = new SHA1HMACSignature(key);
            signature.Method = "GET";
            signature.Path = String.Format("{0}/services", new Uri(url).AbsolutePath);
            signature.Clear();
            signature.AddParameter(category.Value);
            signature.AddParameter(keyword);
            signature.AddParameter(offset);
            signature.AddParameter(limit);

            WebRequest request = WebRequest.Create(String.Format(
                "{0}/services?{1}",
                url,
                string.Join("&", map.Where(
                    f => f.Value != null
                    ).ToDictionary(
                    x => x.Key, x => x.Value
                    ).Select(kvp =>
                    string.Format("{0}={1}", kvp.Key, HttpUtility.UrlEncode(kvp.Value.ToString())))
                    )
                ));
            request.Method = "GET";
            request.Headers.Add("X-Agent", agent);
            request.Headers.Add("X-Signature", signature.Calculate());
            request.Headers.Add("X-Format", "XML");
            request.Headers.Add("X-Language", this.language.Value);

            WebResponse response = request.GetResponse();
            using (Stream stream = response.GetResponseStream())
            {
                StreamReader reader = new StreamReader(stream);
                XmlSerializer serializer =
                    new XmlSerializer(typeof(Response));
                Response r = (Response)serializer.Deserialize(reader);
                response.Close();
                if (r.Code != "0")
                {
                    throw new Error(r.Code, r.Message);
                }
                return r.ServiceList;
            }
        }

        public List<Option> GetOptionList(string id, Dictionary<string, object> dictionary)
        {
            if(agent == null)
            {
                throw new InvalidOperationException(
                    "Not authenticated. Call 'SetAuthenticationData(...)' first"
                    );
            }
            SHA1HMACSignature signature = new SHA1HMACSignature(key);
            signature.Method = "GET";
            signature.Path = String.Format("{0}/options", new Uri(url).AbsolutePath);
            signature.Clear();
            signature.AddParameter(id);
            if(dictionary != null)
            {
                foreach(KeyValuePair<string, object> kvp in dictionary.OrderBy(e => e.Key))
                {
                    if(kvp.Value != null)
                    {
                        signature.AddParameter(kvp.Value);
                    }
                }
            }
            Dictionary<string, object> map =
                new Dictionary<string, object>();
            map.Add("service", id);
            foreach(KeyValuePair<string, object> kvp in dictionary)
            {
                map.Add(String.Format("({0})", kvp.Key), kvp.Value);
            }
            WebRequest request = WebRequest.Create(String.Format(
                "{0}/options?{1}",
                url,
                string.Join("&", map.Where(
                    f => f.Value != null
                    ).ToDictionary(
                    x => x.Key, x => x.Value
                    ).Select(kvp =>
                    string.Format("{0}={1}", kvp.Key, HttpUtility.UrlEncode(kvp.Value.ToString())))
                    )
                ));
            request.Method = "GET";
            request.Headers.Add("X-Agent", agent);
            request.Headers.Add("X-Signature", signature.Calculate());
            request.Headers.Add("X-Format", "XML");
            request.Headers.Add("X-Language", this.language.Value);

            WebResponse response = request.GetResponse();
            using (Stream stream = response.GetResponseStream())
            {
                StreamReader reader = new StreamReader(stream);
                System.Xml.Serialization.XmlSerializer serializer =
                    new System.Xml.Serialization.XmlSerializer(typeof(Response));
                Response r = (Response)serializer.Deserialize(reader);
                response.Close();
                if (r.Code != "0")
                {
                    throw new Error(r.Code, r.Message);
                }
                return r.OptionList;
            }
        }

        public string Register(string service, int amount, int fee, int currency, string transactionId, DateTime date, Channel channel, Source source, string description, string tag, Dictionary<string, object> dictionary)
        {
            if (agent == null)
            {
                throw new InvalidOperationException(
                    "Not authenticated. Call 'SetAuthenticationData(...)' first"
                    );
            }
            SHA1HMACSignature signature = new SHA1HMACSignature(key);
            signature.Method = "POST";
            signature.Path = String.Format("{0}/payments", new Uri(url).AbsolutePath);
            signature.Clear();
            signature.AddParameter(service);
            signature.AddParameter(amount);
            signature.AddParameter(fee);
            signature.AddParameter(currency);
            signature.AddParameter(transactionId);
            signature.AddParameter(date.ToString("yyyy-MM-dd'T'HH:mm:ss"));
            signature.AddParameter(channel);
            signature.AddParameter(source);
            signature.AddParameter(description);
            signature.AddParameter(tag);
            if (dictionary != null)
            {
                foreach (KeyValuePair<string, object> kvp in dictionary.OrderBy(e => e.Key))
                {
                    if (kvp.Value != null)
                    {
                        signature.AddParameter(kvp.Value);
                    }
                }
            }
            WebRequest request = WebRequest.Create(
                String.Format("{0}/payments", url)
                );
            request.Method = "POST";
            request.Headers.Add("X-Agent", agent);
            request.Headers.Add("X-Signature", signature.Calculate());
            request.Headers.Add("X-Format", "XML");
            request.Headers.Add("X-Language", this.language.Value);

            Dictionary<string, object> map =
                new Dictionary<string, object>();
            map.Add("service", service);
            map.Add("amount", amount);
            map.Add("fee", fee);
            map.Add("currency", currency);
            map.Add("transaction-id", transactionId);
            map.Add("date", date.ToString("yyyy-MM-dd'T'HH:mm:ss"));
            map.Add("channel", channel);
            map.Add("source", source);
            map.Add("description", description);
            map.Add("tag", tag);
            foreach (KeyValuePair<string, object> kvp in dictionary)
            {
                map.Add(String.Format("({0})", kvp.Key), kvp.Value);
            }
            string data = string.Join("&", map.Where(
                f => f.Value != null
                ).ToDictionary(
                x => x.Key, x => x.Value
                ).Select(kvp =>
                string.Format("{0}={1}", kvp.Key, HttpUtility.UrlEncode(kvp.Value.ToString(), Encoding.UTF8)))
                );
            byte[] array = Encoding.UTF8.GetBytes(data);
            request.ContentType = "application/x-www-form-urlencoded";
            request.ContentLength = array.Length;
            request.GetRequestStream().Write(array);

            WebResponse response = request.GetResponse();
            using (Stream stream = response.GetResponseStream())
            {
                StreamReader reader = new StreamReader(stream);
                System.Xml.Serialization.XmlSerializer serializer =
                    new System.Xml.Serialization.XmlSerializer(typeof(Response));
                Response r = (Response)serializer.Deserialize(reader);
                response.Close();
                if (r.Code != "0")
                {
                    throw new Error(r.Code, r.Message);
                }
                return r.Reference;
            }
        }

        public Payment Pay(string reference)
        {
            if (agent == null)
            {
                throw new InvalidOperationException(
                    "Not authenticated. Call 'SetAuthenticationData(...)' first"
                    );
            }
            SHA1HMACSignature signature = new SHA1HMACSignature(key);
            signature.Method = "POST";
            signature.Path = String.Format("{0}/payments/{1}", new Uri(url).AbsolutePath, reference);
            signature.Clear();
            WebRequest request = WebRequest.Create(
                String.Format("{0}/payments/{1}", url, reference)
                );
            request.Method = "POST";
            request.Headers.Add("X-Agent", agent);
            request.Headers.Add("X-Signature", signature.Calculate());
            request.Headers.Add("X-Format", "XML");
            request.Headers.Add("X-Language", this.language.Value);

            WebResponse response = request.GetResponse();
            using (Stream stream = response.GetResponseStream())
            {
                StreamReader reader = new StreamReader(stream);
                System.Xml.Serialization.XmlSerializer serializer =
                    new System.Xml.Serialization.XmlSerializer(typeof(Response));
                Response r = (Response)serializer.Deserialize(reader);
                response.Close();
                if (r.Code != "0")
                {
                    throw new Error(r.Code, r.Message);
                }
                return r.PaymentList[0];
            }
        }

        public Payment GetPayment(string reference = null, string transactionId = null)
        {
            if (agent == null)
            {
                throw new InvalidOperationException(
                    "Not authenticated. Call 'SetAuthenticationData(...)' first"
                    );
            }
            SHA1HMACSignature signature = new SHA1HMACSignature(key);
            signature.Method = "GET";
            signature.Path = String.Format("{0}/payments/{1}", new Uri(url).AbsolutePath,
                reference != null ?
                reference :
                transactionId
                );
            signature.Clear();
            WebRequest request = WebRequest.Create(
                String.Format("{0}/payments/{1}", url,
                    reference != null ?
                    reference :
                    transactionId
                    )
                );
            request.Method = "GET";
            request.Headers.Add("X-Agent", agent);
            request.Headers.Add("X-Signature", signature.Calculate());
            request.Headers.Add("X-Format", "XML");
            request.Headers.Add("X-Language", this.language.Value);

            WebResponse response = request.GetResponse();
            using (Stream stream = response.GetResponseStream())
            {
                StreamReader reader = new StreamReader(stream);
                XmlSerializer serializer =
                    new XmlSerializer(typeof(Response));
                Response r = (Response)serializer.Deserialize(reader);
                response.Close();
                if (r.Code != "0")
                {
                    throw new Error(r.Code, r.Message);
                }
                return r.PaymentList[0];
            }
        }

        public List<Payment> GetPaymentList(
            string tag = null,
            DateTime? from = null,
            DateTime? to = null,
            int offset = 0,
            int limit = 100
            )
        {
            if (agent == null)
            {
                throw new InvalidOperationException(
                    "Not authenticated. Call 'SetAuthenticationData(...)' first"
                    );
            }
            Dictionary<string, object> map =
                new Dictionary<string, object>();
            map.Add("tag", tag);
            map.Add("from", from != null ? from.Value.ToString("yyyy-MM-dd'T'HH:mm:ss") : null);
            map.Add("to",     to != null ?   to.Value.ToString("yyyy-MM-dd'T'HH:mm:ss") : null);
            map.Add("offset", offset);
            map.Add("limit", limit);

            SHA1HMACSignature signature = new SHA1HMACSignature(key);
            signature.Method = "GET";
            signature.Path = String.Format(
                "{0}/payments",
                new Uri(url).AbsolutePath
                );
            signature.Clear();
            signature.AddParameter(tag);
            signature.AddParameter(map["from"]);
            signature.AddParameter(map["to"]);
            signature.AddParameter(offset);
            signature.AddParameter(limit);

            WebRequest request = WebRequest.Create(String.Format(
                "{0}/payments?{1}",
                url,
                string.Join("&", map.Where(
                    f => f.Value != null
                    ).ToDictionary(
                    x => x.Key, x => x.Value
                    ).Select(kvp =>
                    string.Format("{0}={1}", kvp.Key, HttpUtility.UrlEncode(kvp.Value.ToString())))
                    )
                ));
            request.Method = "GET";
            request.Headers.Add("X-Agent", agent);
            request.Headers.Add("X-Signature", signature.Calculate());
            request.Headers.Add("X-Format", "XML");
            request.Headers.Add("X-Language", this.language.Value);

            WebResponse response = request.GetResponse();
            using (Stream stream = response.GetResponseStream())
            {
                StreamReader reader = new StreamReader(stream);
                XmlSerializer serializer =
                    new XmlSerializer(typeof(Response));
                Response r = (Response)serializer.Deserialize(reader);
                response.Close();
                if (r.Code != "0")
                {
                    throw new Error(r.Code, r.Message);
                }
                return r.PaymentList;
            }
        }
    }
}