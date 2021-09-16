using System;
using System.Xml.Serialization;
using System.Collections.Generic;

namespace STREAM
{
    [Serializable]
    public class Payment
    {
        [XmlElement("id")]
        public int id { get; set; }
        [XmlElement("reference")]
        public string Reference { get; set; }
        [XmlElement("service")]
        public Service Service { get; set; }
        [XmlElement("amount")]
        public decimal Amount { get; set; }
        [XmlElement("fee")]
        public decimal Fee { get; set; }
        [XmlElement("currency")]
        public int Currency { get; set; }
        [XmlElement("iso4217")]
        public string ISO4217 { get; set; }
        [XmlElement("date")]
        public DateTime Date { get; set; }
        [XmlElement("transaction-id")]
        public string TransactionId { get; set; }
        [XmlElement("tag")]
        public string Tag { get; set; }
        [XmlElement("source")]
        public Source Origin { get; set; }
        [XmlElement("detail")]
        public List<Detail> DetailList { get; set; }
        [XmlElement("status")]
        public int Status { get; set; }

        public Payment()
        {
            this.DetailList = new List<Detail>();
        }
    }
}