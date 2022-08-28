using lab_7.Repository;
using lab_7.Domain;

using System;
using System.Collections.Generic;
using System.Linq;

namespace lab_7.Services
{
    class AppService
    {
        private IRepository<Double, Echipa> echipaRepository;
        private IRepository<Double, Jucator> jucatorRepository;
        private IRepository<Double, Meci> meciRepository;
        private IRepository<Tuple<Double, Double>, JucatorActiv> jucatorActivRepository;

        public AppService(IRepository<Double, Echipa> echipaRepository, IRepository<Double, Jucator> jucatorRepository, IRepository<Double, Meci> meciRepository, IRepository<Tuple<Double, Double>, JucatorActiv> jucatorActivRepository)
        {
            this.echipaRepository = echipaRepository;
            this.jucatorRepository = jucatorRepository;
            this.meciRepository = meciRepository;
            this.jucatorActivRepository = jucatorActivRepository;
        }

        public List<Echipa> FindAllEchipe()
        {
            return echipaRepository.FindAll().ToList();
        }

        public List<Jucator> FindAllJucatori()
        {
            return jucatorRepository.FindAll().ToList();
        }

        public List<Meci> FindAllMeciuri()
        {
            return meciRepository.FindAll().ToList();
        }

        public List<JucatorActiv> FindAllJucatoriActivi()
        {
            return jucatorActivRepository.FindAll().ToList();
        }

        // Sa se afiseze toti jucatorii unei echipe date
        public List<Jucator> FindJucatori(String nume)
        {
            IEnumerable<Jucator> jucatori = from jucator in jucatorRepository.FindAll()
                                            where jucator.Echipa.Nume.Equals(nume)
                                            select jucator;
            return jucatori.ToList();
        }

        // Sa se afiseze toti jucatorii activi ai unei echipe de la un anumit meci
        public List<JucatorActiv> FindJucatoriActivi(String echipa, Double meci)
        {
            IEnumerable<JucatorActiv> jucatoriActivi = from jA in jucatorActivRepository.FindAll()
                                                       where jA.IdMeci.Equals(meci)
                                                       join j in jucatorRepository.FindAll() on jA.IdJucator equals j.ID
                                                       join e in echipaRepository.FindAll() on j.Echipa.ID equals e.ID
                                                       where e.Nume.Equals(echipa)
                                                       select jA;
            return jucatoriActivi.ToList();
        }

        // Sa se afiseze toate meciurile dintr-o anumita perioada calendaristica 
        public List<Meci> FindMeciuri(DateTime fro, DateTime to)
        {
            IEnumerable<Meci> meciuri = from meci in meciRepository.FindAll()
                                        where meci.Data.CompareTo(fro) > 0 && meci.Data.CompareTo(to) < 0
                                        select meci;
            return meciuri.ToList();
        }

        // Sa se determine si sa se afiseze scorul de la un anumit meci
        public Tuple<Double, Double> FindScor(Double id)
        {
            var echipe = (from meci in meciRepository.FindAll()
                         where meci.ID.Equals(id)
                         select new
                         {
                            Echipa1 = meci.Echipa1,
                            Echipa2 = meci.Echipa2
                         }).ElementAt(0);
            Double scor1 = (from jA in jucatorActivRepository.FindAll()
                        where jA.IdMeci.Equals(id)
                        join j in jucatorRepository.FindAll() on jA.IdJucator equals j.ID
                        join e in echipaRepository.FindAll() on j.Echipa.ID equals e.ID
                        where e.ID.Equals(echipe.Echipa1.ID)
                        select jA).Sum(jA => jA.NrPuncteInscrise);
            Double scor2 = (from jA in jucatorActivRepository.FindAll()
                            where jA.IdMeci.Equals(id)
                            join j in jucatorRepository.FindAll() on jA.IdJucator equals j.ID
                            join e in echipaRepository.FindAll() on j.Echipa.ID equals e.ID
                            where e.ID.Equals(echipe.Echipa2.ID)
                            select jA).Sum(jA => jA.NrPuncteInscrise);
            return new Tuple<Double, Double>(scor1, scor2);
        }
    }
}
