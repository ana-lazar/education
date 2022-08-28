using lab_7.Services;
using lab_7.Model.Validator;
using lab_7.Domain;
using lab_7.Repository;
using lab_7.Interface;

using System;

namespace lab_7
{
    class Coordinator
    {
        public void run()
        {
            AppService service = GetService();
            Ui ui = new Ui(service);
            ui.start();
        }

        private static AppService GetService()
        {
            AppService service = new AppService(
                GetEchipeRepository(),
                GetJucatorRepository(),
                GetMeciRepository(),
                GetJucatorActivRepository()
            );
            return service;
        }

        private static IRepository<Double, Echipa> GetEchipeRepository()
        {
            string fileName = "../../../data/echipe.txt";
            IValidator<Echipa> validator = new EchipaValidator();

            IRepository<Double, Echipa> repository = new EchipeInFileRepository(validator, fileName);
            return repository;
        }

        private static IRepository<Double, Jucator> GetJucatorRepository()
        {
            string fileName = "../../../data/jucatori.txt";
            IValidator<Jucator> validator = new JucatorValidator();

            IRepository<Double, Jucator> repository = new JucatorInFileRepository(validator, fileName);
            return repository;
        }

        private static IRepository<Double, Meci> GetMeciRepository()
        {
            string fileName = "../../../data/meciuri.txt";
            IValidator<Meci> validator = new MeciValidator();

            IRepository<Double, Meci> repository = new MeciInFileRepository(validator, fileName);
            return repository;
        }

        private static IRepository<Tuple<Double, Double>, JucatorActiv> GetJucatorActivRepository()
        {
            string fileName = "../../../data/jucatoriActivi.txt";
            IValidator<JucatorActiv> validator = new JucatorActivValidator();

            IRepository<Tuple<Double, Double>, JucatorActiv> repository = new JucatorActivInFileRepository(validator, fileName);
            return repository;
        }
    }
}
