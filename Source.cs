using System;
using System.Xml.Serialization;

namespace STREAM
{
    [Serializable]
    public enum Source
    {
        BankAccount,
        Cash,
        Card,
        Cryptocurrency,
        Wallet
    }
}