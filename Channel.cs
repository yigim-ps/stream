using System;
using System.Xml.Serialization;

namespace STREAM
{
    [Serializable]
    public enum Channel
    {
        ATM,
        POS,
        Web,
        MobileApplication,
        Kiosk
    }
}