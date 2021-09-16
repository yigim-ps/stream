using System;

namespace STREAM
{
    public class Error : Exception
    {
        public string Code { get; set; }
        public string Message { get; set; }

        public Error(string code, string message, params Object[] args)
        {
            this.Code = code;
            this.Message = String.Format(message, args);
        }
    }
}