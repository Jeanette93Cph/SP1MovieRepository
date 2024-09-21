package dat.daos;

import dat.dtos.DirectorDTO;
import dat.entities.Director;
import dat.entities.Movie;
import dat.exceptions.JpaException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.util.ArrayList;
import java.util.List;

public class DirectorDAO
{
    private static DirectorDAO directorDAO;

    private static EntityManagerFactory emf;

    public DirectorDAO(EntityManagerFactory emf)
    {
        this.emf = emf;
    }

    public static DirectorDAO getInstance(EntityManagerFactory emf)
    {
        if(directorDAO == null)
        {
            directorDAO = new DirectorDAO(emf);
        }
        return directorDAO;
    }


    //Persist one director
    public DirectorDTO persistEntity(DirectorDTO directorDTO)
    {
        Director director = new Director();
        director.setId(directorDTO.getId());
        director.setName(directorDTO.getName());

        try(EntityManager em = emf.createEntityManager())
        {
            em.getTransaction().begin();
            em.persist(director);
            em.getTransaction().commit();
        }
        catch(Exception e)
        {
            throw new JpaException("Failed to persist director:" + e.getMessage());
        }
        return new DirectorDTO(director);
    }


    //Persist a list of directors
    public List<DirectorDTO> persistListOfDirectors(List<DirectorDTO> directorDTOList)
    {
        List<DirectorDTO> persistedlist = new ArrayList<>();
        try(EntityManager em = emf.createEntityManager())
        {
            em.getTransaction().begin();

            for(DirectorDTO dto : directorDTOList)
            {
                if(em.find(Director.class, dto.getId()) == null)
                {
                    Director director = new Director(dto);
                    em.persist(director);
                    dto.setId(director.getId());
                    persistedlist.add(dto);
                }
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Failed to persist list of directors: ");
            e.printStackTrace();

        }
        return persistedlist;
    }


    public static List<DirectorDTO> findAll()
    {
         try(EntityManager em = emf.createEntityManager())
         {
             return em.createQuery("SELECT new dat.dtos.DirectorDTO(d) FROM Director d", DirectorDTO.class).getResultList();
         }
         catch(Exception e){
             throw new JpaException("Failed to find all directors.");
         }
    }

    public static DirectorDTO findEntity(Long id)
    {
        try(EntityManager em = emf.createEntityManager())
        {
            Director director = em.find(Director.class, id);
            if(director == null)
            {
                throw new JpaException("No director found with id: " + id);
            }
            return new DirectorDTO(em.find(Director.class, id));
        }
        catch (Exception e) {
            throw new JpaException("Failed to find director.");
        }
    }

    public static DirectorDTO updateEntity(DirectorDTO directorDTO, Long id)
    {
        try(EntityManager em = emf.createEntityManager())
        {
            em.getTransaction().begin();
            Director director = em.find(Director.class, id);

            if(director == null)
            {
                throw new JpaException("No director found with id: " + id);
            }

            director.setName(directorDTO.getName());
            director.setId(directorDTO.getId());

            em.merge(director);
            em.getTransaction().commit();

            return new DirectorDTO(director);

        }
        catch (Exception e)
        {
            System.out.println("Failed to update director: ");
            e.printStackTrace();
        }
        return null;
    }



    public static void removeEntity(Long id)
    {
        try(EntityManager em = emf.createEntityManager())
        {
            Director director = em.find(Director.class, id);
            if (director == null) {
                throw new JpaException("No director found with id: " + id);
            }
            em.getTransaction().begin();
            em.remove(director);
            em.getTransaction().commit();
        }
        catch (Exception e)
        {
            System.out.println("Failed to delete director: ");
            e.printStackTrace();
        }

    }





}
