using System;
using System.Xml.Serialization;

namespace STREAM
{
    [Serializable]
    public class Field
    {
        [XmlAttribute("type")]
        public TYPE Type { get; set; }
        [XmlAttribute("name")]
        public string Name { get; set; }
        [XmlAttribute("label")]
        public string Label { get; set; }
        [XmlAttribute("description")]
        public string Description { get; set; }
        [XmlAttribute("hint")]
        public string Hint { get; set; }
        [XmlAttribute("capacity")]
        public int Capacity { get; set; }
        [XmlAttribute("pattern")]
        public string Pattern { get; set; }
        [XmlAttribute("editable")]
        public bool Editable { get; set; }
        [XmlAttribute("order")]
        public int Order { get; set; }
        [XmlAttribute("show")]
        public bool Show { get; set; }
        [XmlAttribute("active")]
        public bool Active { get; set; }

        public Field()
        {
        }

        [XmlType(Namespace = "Field")]
        public enum TYPE
        {
            DateTime,
            Text,
            Password,
            URL,
            Email,
            Numeric,
            Checkbox,
            Radio,
            MSISDN,
            Amount,
            List,
            Hidden
        }
    }
}