package motorcycleraces;

public class Main {
    public static void main(String[] args) throws Exception {
/*
        Properties props = new Properties();
        try {
            props.load(new FileReader("bd.config"));
        } catch (IOException e) {
            System.out.println("Cannot find bd.config " + e);
        }

        SpectacolValidator spectacolValidator = new SpectacolValidator();
        RepositoryAngajat repoAngajat = new RepositoryAngajat(props);
        RepositorySpectacol repoSpectacol = new RepositorySpectacol(props, spectacolValidator);

        BiletValidator biletValidator = new BiletValidator(repoSpectacol);
        RepositoryBilet repoBilet = new RepositoryBilet(props, repoSpectacol, biletValidator);

        System.out.println(repoAngajat.SearchForUser("andreea", "ziuanastere"));
        System.out.println(repoSpectacol.filterByDay(16));
        Spectacol spectacol = repoSpectacol.findOne(1L);
        System.out.println(spectacol);
        System.out.println(repoSpectacol.findAll());

        Bilet bilet = new Bilet(spectacol, "mark", 17);
        repoBilet.save(bilet);
        spectacol.setLocuriDisponibile(spectacol.getLocuriDisponibile() - bilet.getNumarDeLocuri());
        spectacol.setLocuriOcupate(spectacol.getLocuriOcupate() + bilet.getNumarDeLocuri());
        repoSpectacol.update(spectacol);
        System.out.println(repoBilet.findOne(1L));
        System.out.println(repoBilet.findAll());

 */
        MainApp.main(args);
    }
}