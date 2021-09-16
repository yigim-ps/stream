using System;
using System.Collections;
using System.Collections.Generic;
using System.Linq;
using System.Security.Cryptography;
using System.Text;

namespace STREAM
{
    public class SHA1HMACSignature
    {
        private string key;
        private string method = "GET";
        private string path = "/";
        private List<object> list =
            new List<object>();

        public string Key { set { this.key = value; } }
        public string Method { set { this.method = value; } }
        public string Path { set { this.path = value; } }

        public SHA1HMACSignature(string key)
        {
            this.key = key;
        }

        public void AddParameter(object value)
        {
            if (value != null)
            {
                this.list.Add(value);
            }
        }

        public string Calculate()
        {
            TimeSpan span = DateTime.UtcNow - new DateTime(
                1970, 1, 1, 0, 0, 0, DateTimeKind.Utc
                );
            return Calculate((long)span.TotalMilliseconds / 200);
        }

        public string Calculate(long time)
        {
            string data = String.Format(
                "{0}{1}{2}{3}{4}{5}",
                method.Length,
                method,
                path.Length,
                path,
                string.Join("", list.Select(e =>
                    string.Format("{0}{1}", e.ToString().Length, e))
                    ),
                time
                );
            byte[] array = Encoding.UTF8.GetBytes(
                data
                ).ToArray();
            using (HMACSHA1 mac = new HMACSHA1(Encoding.ASCII.GetBytes(key)))
            {
                return Convert.ToBase64String(
                    mac.ComputeHash(array)
                    );
            }
        }

        public void Clear()
        {
            list.Clear();
        }
    }
}