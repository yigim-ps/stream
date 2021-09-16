using System;
using System.Xml.Serialization;

namespace STREAM
{
    [Serializable]
    public class Currency
    {
        [XmlAttribute("code")]
        public string Code { get; set; }
        [XmlAttribute("iso4217")]
        public string ISO4217 { get; set; }
        [XmlAttribute("name")]
        public string Name { get; set; }
        [XmlAttribute("description")]
        public string Description { get; set; }
        [XmlAttribute("min")]
        public decimal Min { get; set; }
        [XmlAttribute("max")]
        public decimal Max { get; set; }
        [XmlAttribute("active")]
        public bool Active { get; set; }

        public Currency()
        { 
        }
    }
}