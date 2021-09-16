using System;
using System.Xml.Serialization;

namespace STREAM
{
    [Serializable]
    public class Category
    {
        [XmlElement("id")]
        public int ID { get; set; }
        [XmlElement("name")]
        public string Name { get; set; }
        [XmlElement("description")]
        public string Description { get; set; }
        [XmlElement("alias")]
        public string Alias { get; set; }
        [XmlElement("order")]
        public int Order { get; set; }
        [XmlElement("empty")]
        public bool Empty { get; set; }
        [XmlElement("active")]
        public bool Active { get; set; }

        public Category()
        {
        }
    }
}