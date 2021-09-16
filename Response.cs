using System;
using System.Collections.Generic;
using System.Xml.Serialization;

namespace STREAM
{
    [Serializable, XmlRoot("response")]
    public class Response
    {
        [XmlAttribute("code")]
        public string Code { get; set; }
        [XmlAttribute("message")]
        public string Message { get; set; }
        [XmlAttribute("type")]
        public string Type { get; set; }
        [XmlElement("option")]
        public List<Option> OptionList
        {
            get;
            set;
        } = new List<Option>();
        [XmlElement("reference")]
        public string Reference { get; set; }
        [XmlElement("payment")]
        public List<Payment> PaymentList
        {
            get;
            set;
        } = new List<Payment>();
        [XmlElement("service")]
        public List<Service> ServiceList
        {
            get;
            set;
        } = new List<Service>();
        [XmlElement("category")]
        public List<Category> CategoryList
        {
            get;
            set;
        } = new List<Category>();

        public Response()
        {
        }
    }
}