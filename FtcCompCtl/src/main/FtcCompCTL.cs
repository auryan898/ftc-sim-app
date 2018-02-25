using System;
using MRI_Core_Library;


namespace FTCCompCTL
{
    public class LegacyModule : CoreLegacyModule
    {
        public LegacyModule() : base()
        {

        }
        public LegacyModule(string value) : base(value)
        {

        }
    }
    public class Touch : NXT
    {
        protected bool isPressed;
        public Touch(LegacyModule legacy, int nxt_port) : base(legacy, nxt_port)
        { }
        public bool IsPressed()
        {
            int value = readAnalog();
            isPressed = value < 500;

            return isPressed;
        }

    }
    public class LightSensor : NXT
    {
        private double min = 0;
        private double max = 900;

        public LightSensor(LegacyModule legacy, int nxt_port) : base(legacy, nxt_port) { }
        public double getLightDetected()
        {
            double light = 900 - readAnalog();
            light = light / 720.0;
            light = Range.clip(light, -1, 1);
            return light;
        }
        public double getRawLightDetected()
        {
            return 900 - readAnalog();
        }
    }
    public class NXT
    {
        protected LegacyModule legacy;
        public int nxt_port  { get; set; }
        public NXT(LegacyModule legacy, int nxt_port)
        {
            this.legacy = legacy;
            this.nxt_port = nxt_port;
        }
        public int readAnalog()
        {

            return legacy.readAnalog(this.nxt_port);
        }
        public byte[] readI2C(byte address, byte register, byte length, bool volt9)
        {
            System.Threading.Thread.Sleep(100);
            return legacy.readI2C(nxt_port, address, register, length, volt9);
        }
        public byte readI2C(byte address, byte register)
        {
            System.Threading.Thread.Sleep(100);
            return legacy.readI2C(nxt_port, address, register);
        }
        public void writeI2C(byte address, byte register, byte[] data, bool volt9)
        {
            System.Threading.Thread.Sleep(100);
            legacy.writeI2C(nxt_port, address, register, data, volt9);
        }
    }
    public class Range
    {
        public static double clip(double value, double min, double max)
        {
            if (value < min)
                value = min;
            if (value > max)
                value = max;
            return value;
        }
    }
    public class DcMotorController : NXT
    {
        byte address = 0x05 >> 1;
        bool volt9 = false;

        public DcMotorController(LegacyModule legacy, int nxt_port) : base(legacy, nxt_port)
        {

        }
        public char[] bytesTochars(byte[] data)
        {
            char[] words = new char[data.GetLength(0)];
            for (int j = 0; j < words.GetLength(0) - 4; j++)
            {
                words[j] = Convert.ToChar(data[j + 4]);
            }
            return words;
        }
        public string charsToString(char[] letters)
        {
            string result = "";
            for (int j = 0; j < letters.GetLength(0); j++)
            {
                result += letters[j];
            }
            return result;
        }
        

        public string getManufacturer()
        {
            byte register = 0x08;
            byte length = 16;
            byte[] data = readI2C(address, register, length, volt9);
            return charsToString(bytesTochars(data));
        }
        public string getVersion()
        {
            byte register = 0x00;
            byte length = 8;
            byte[] data = readI2C(address, register, length, volt9);
            return charsToString(bytesTochars(data));
        }
        public double getMotorPower(int port)
        {
            byte register;
            switch(port){
                case 2:
                    register = 0x46;
                    break;
                default:
                    register = 0x45;
                    break;
            }
            byte length = 1;
            byte[] data = readI2C(address, register, length, volt9);
            return ((int)data[4]) * 2.0 / 255.0 - 1;
        }
        public void setMotorPower(double power, int port)
        {
            byte value = (byte)(sbyte)Range.clip(power * 255.0, -127, 128);
            byte register;
            switch(port){
                case 2:
                    register = 0x46;
                    break;
                default:
                    register = 0x45;
                    break;
            }
            byte[] data = { value };
            writeI2C(address, register, data, volt9);
        }
        
        public int getMotorCurrentPosition(int motor,int port){
            byte register;
            switch(port){
                case 2:
                    register = 0x40;
                    break;
                default:
                    register = 0x45;
                    break;
            }
            byte length = 4;
            byte[] data = readI2C(address, register, length, volt9);
            // the data converts to a long data[4] -> data[7]
            //return BitConverter.ToInt64(data,4);
            return 0;
            }
        public int getMotorTargetPosition(int motor){return 0;}
        public void setMotorTargetPosition(int motor, int postition){}
        
        public int getMotorMode(int motor){return 0;}
        public void setMotorMode(int motor, int mode){}
        
    }
    public class DcMotor
    {
        DcMotorController controller;
        int motor_port;
        int nxt_port;
        
        public DcMotor(DcMotorController controller, int motor_port)
        {
            this.controller = controller;
            this.motor_port = motor_port;
            this.nxt_port   = controller.nxt_port;
        }
        public void getPower(){}
        public void setPower(double power){}
        public void getMode(){}
        public void setMode(){}

        public void getController(){}
        
        public void getCurrentPosition(){}
        public void getTargetPosition(){}
        public void setTargetPosition(int position){}
    }
}
