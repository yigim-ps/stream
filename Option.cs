using System;
using System.Collections;
using System.Collections.Generic;
using System.Xml.Serialization;
using System.Linq;

namespace STREAM
{
    [Serializable]
    public class Option
    {
        [XmlElement("code")]
        public string ID { get; set; }
        [XmlElement("name")]
        public string Name { get; set; }
        [XmlElement("amount")]
        public decimal Amount { get; set; }
        [XmlElement("min")]
        public decimal Min { get; set; }
        [XmlElement("max")]
        public decimal Max { get; set; }
        [XmlElement("fixed")]
        public bool Fixed { get; set; }
        [XmlElement("currency")]
        public int Currency { get; set; }
        [XmlElement("iso4217")]
        public string ISO4217 { get; set; }
        [XmlElement("fixedAmount")]
        public List<decimal> FixedAmountList { get; set; }
        [XmlElement("color")]
        public string Color { get; set; }
        [XmlElement("detail")]
        public List<Detail> DetailList { get; set; }

        public Option()
        {
            this.FixedAmountList = new List<decimal>();
            this.DetailList = new List<Detail>();
        }

        public Dictionary<string, object> GetDetailListAsDictionary()
        {
            return DetailList.ToDictionary(
                e => e.Name,
                e => (object)e.Value
                );
        }
    }
}