using System;
using System.Collections.Generic;
using System.Net.Sockets;
using System.Runtime.Serialization;
using System.Runtime.Serialization.Formatters.Binary;
using System.Threading;
using log4net;
using MotorcycleCommon.Networking;
using MotorcycleCommon.Services;

namespace motorcycle_contest.Services
{
    public abstract class BaseServiceProxy
    {
        protected static readonly ILog Logger = LogManager.GetLogger("BaseServiceProxy");

        private string _host;
        private int _port;

        // private IMotorcycleObserver client;

        private NetworkStream _stream;
        private IFormatter _formatter;
        protected TcpClient _connection;

        private Queue<Response> _responses = new Queue<Response>();
        private volatile bool _finished;
        private EventWaitHandle _waitHandle;

        public BaseServiceProxy(string host, int port)
        {
            Logger.InfoFormat("creating proxy with host {0} and port {1}", host, port);
            _host = host;
            _port = port;
        }

        protected void GetConnection()
        {
            Logger.Info("ensuring connection to server");
            if (_connection != null)
            {
                Logger.Info("proxy already connected");
                return;
            }
            try
            {
                Logger.Info("creating new connection");
                _connection = new TcpClient(_host, _port);
                _stream = _connection.GetStream();
                _formatter = new BinaryFormatter();
                _finished = false;
                _waitHandle = new AutoResetEvent(false);
                StartReader();
            }
            catch (Exception exception)
            {
                Logger.Warn("creating connection failed");
                throw new ServerException(exception.Message);
            }
        }

        protected void CloseConnection()
        {
            Logger.Info("closing connection to server");
            _finished = true;
            if (_connection == null)
            {
                Logger.Info("connection already closed");
                return;
            }
            TcpClient temp = _connection;
            _connection = null;
            try
            {
                _stream.Close();
                temp.Close();
                _waitHandle.Close();
                // client = null;
                Logger.Info("connection closed");
            }
            catch (Exception exception)
            {
                Logger.Warn("closing connection failed");
                throw new ServerException(exception.Message);
            }
        }

        protected void SendRequest(Request request)
        {
            try
            {
                Logger.InfoFormat("sending request {0}", request);
                _formatter.Serialize(_stream, request);
                _stream.Flush();
            }
            catch (Exception exception)
            {
                Logger.Warn("sending request failed");
                throw new ServerException(exception.Message);
            }
        }

        protected Response ReadResponse()
        {
            Logger.Info("entering ReadResponse function");
            Response response = null;
            try
            {
                _waitHandle.WaitOne();
                lock (_responses)
                {
                    response = _responses.Dequeue();
                }
            }
            catch (Exception exception)
            {
                Logger.Warn("reading response failed");
            }
            Logger.InfoFormat("exiting read response with {0}", response);
            return response;
        }

        private void StartReader()
        {
            Logger.Info("starting reader thread");
            Thread thread = new Thread(Run);
            thread.Start();
        }

        private void Run()
        {
            Logger.Info("running reader thread");
            while (!_finished)
            {
                try
                {
                    Response response = (Response) _formatter.Deserialize(_stream);
                    Logger.InfoFormat("response recieved from server {0}", response);
                    if (response.Type == ResponseType.REGISTERED_PARTICIPANTS)
                    {
                        HandleRegisteredParticipant(response);
                    }
                    else
                    {
                        lock (_responses)
                        {
                            _responses.Enqueue(response);
                        }
                        _waitHandle.Set();
                    }
                }
                catch (SerializationException)
                {
                    CloseConnection();
                    break;
                }
                catch (Exception exception)
                {
                    Logger.Warn("reading error " + exception);
                    CloseConnection();
                    break;
                }
            }
        }

        public abstract void HandleRegisteredParticipant(Response response);
    }
}