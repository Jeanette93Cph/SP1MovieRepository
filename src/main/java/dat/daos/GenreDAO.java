package dat.daos;

import dat.dtos.GenreDTO;
import dat.entities.Genre;
import dat.exceptions.JpaException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.util.ArrayList;
import java.util.List;

public class GenreDAO
{
    private static GenreDAO genreDAO;

    private static EntityManagerFactory emf;

    public GenreDAO(EntityManagerFactory emf)
    {
        this.emf = emf;
    }

    public static GenreDAO getInstance(EntityManagerFactory emf)
    {
        if(genreDAO == null)
        {
            genreDAO = new GenreDAO(emf);
        }
        return genreDAO;
    }


    //Persist one genre
    public static GenreDTO persistEntity(GenreDTO genreDTO)
    {
        Genre genre = new Genre();
        genre.setId(genreDTO.getId());
        genre.setName(genreDTO.getName());

        try(EntityManager em = emf.createEntityManager())
        {
            em.getTransaction().begin();
            em.persist(genre);
            em.getTransaction().commit();
        }
        catch(Exception e)
        {
            throw new JpaException("Failed to persist genre:" + e.getMessage());
        }
        return new GenreDTO(genre);
    }


    //Persist a list of directors
    public List<GenreDTO> persistListOfGenres(List<GenreDTO> genreDTOList)
    {
        List<GenreDTO> persistedlist = new ArrayList<>();
        try(EntityManager em = emf.createEntityManager())
        {
            em.getTransaction().begin();

            for(GenreDTO dto : genreDTOList)
            {
                Genre genre = em.find(Genre.class, dto.getId());

                if(genre == null)
                {
                    genre = new Genre(dto);
                    em.persist(genre);
                } else{
                    genre.setName(dto.getName());
                    genre = em.merge(genre);
                }

                dto.setId(genre.getId());
                persistedlist.add(dto);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Failed to persist list of genres: " + e.getMessage());
            e.printStackTrace();

        }
        return persistedlist;
    }


    public static List<GenreDTO> findAll()
    {
        try(EntityManager em = emf.createEntityManager())
        {
            return em.createQuery("SELECT new dat.dtos.GenreDTO(g) FROM Genre g", GenreDTO.class).getResultList();
        }
        catch(Exception e){
            throw new JpaException("Failed to find all genres." + e.getMessage());
        }
    }

    public static GenreDTO findEntity(Long id)
    {
        try(EntityManager em = emf.createEntityManager())
        {
            Genre genre = em.find(Genre.class, id);
            if(genre == null)
            {
                throw new JpaException("No genre found with id: " + id);
            }
            return new GenreDTO(em.find(Genre.class, id));
        }
        catch (Exception e) {
            throw new JpaException("Failed to find genre." + e.getMessage());
        }
    }

    public static GenreDTO updateEntity(GenreDTO genreDTO, Long id)
    {
        try(EntityManager em = emf.createEntityManager())
        {
            em.getTransaction().begin();
            Genre genre = em.find(Genre.class, id);

            if(genre == null)
            {
                throw new JpaException("No genre found with id: " + id);
            }

            genre.setName(genreDTO.getName());
            genre.setId(genreDTO.getId());

            em.merge(genre);
            em.getTransaction().commit();

            return new GenreDTO(genre);
        }
        catch (Exception e)
        {
            System.out.println("Failed to update genre: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }



    public static void removeEntity(Long id)
    {
        try(EntityManager em = emf.createEntityManager())
        {
            Genre genre = em.find(Genre.class, id);
            if (genre == null) {
                throw new JpaException("No genre found with id: " + id);
            }
            em.getTransaction().begin();
            em.remove(genre);
            em.getTransaction().commit();
        }
        catch (Exception e)
        {
            System.out.println("Failed to delete genre: " + e.getMessage());
            e.printStackTrace();
        }

    }
}
