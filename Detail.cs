using System;
using System.Xml.Serialization;

namespace STREAM
{
    [Serializable]
    public class Detail
    {
        [XmlAttribute("name")]
        public string Name { get; set; }
        [XmlAttribute("label")]
        public string Label { get; set; }
        [XmlText]
        public string Value { get; set; }
        [XmlAttribute("displayable")]
        public bool Displayable { get; set; }

        public Detail()
        { 
        }

        public Detail(string name, string value)
        {
            this.Name = name;
            this.Value = value;
        }
    }
}