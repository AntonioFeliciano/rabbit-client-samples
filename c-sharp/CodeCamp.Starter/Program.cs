using System.Collections.Generic;
using RabbitClient;

namespace CodeCamp.Starter
{
    class Program
    {
        static void Main(string[] args)
        {
            var settings = new RabbitConnectionSettings
            {
                HostName = "localhost",
                UserName = "guest",
                Password = "guest",
                Port = 5672,
                VirtualHost = "/"
            };
            var client = new SimpleRabbitClient(settings, new JsonByteArraySerializer());

        }
    }

    public class OrderFilledMessage
    {
        public string Customer { get; set; }
        public string Address { get; set; }
        public List<Pizza> Pizzas { get; set; }
        public string CouponCode { get; set; }
        public decimal OrderPrice { get; set; }
    }

    public class Pizza
    {
        public string Size { get; set; }
        public List<string> Toppings { get; set; }
    }

    public class MyOrder
    {
        public string Customer { get; set; }
        public string Address { get; set; }
        public List<Pizza> Pizzas { get; set; }
    }

    public class CouponOrder : MyOrder
    {
        public string Coupon { get; set; }
    }
}
