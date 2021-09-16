using System;
using System.Xml.Serialization;
using System.Collections.Generic;

namespace STREAM
{
    [Serializable]
    public class Service
    {
        [XmlElement("id")]
        public int ID { get; set; }
        [XmlElement("category")]
        public Category Category { get; set; }
        [XmlElement("type")]
        public TYPE Type { get; set; }
        [XmlElement("name")]
        public string Name { get; set; }
        [XmlElement("description")]
        public string Description { get; set; }
        [XmlElement("alias")]
        public string Alias { get; set; }
        [XmlElement("new")]
        public bool New { get; set; }
        [XmlElement("field")]
        public List<Field> FieldList { get; set; }
        [XmlElement("currency")]
        public List<Currency> Currency { get; set; }
        [XmlElement("order")]
        public int Order { get; set; }
        [XmlElement("active")]
        public bool Active { get; set; }

        public Service()
        {
        }

        [XmlType(Namespace = "Service")]
        public enum TYPE
        {
            Direct, Phased
        }
    }
}