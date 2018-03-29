package componentArchitecture;


import java.util.*;

public class MetaEntity {
    public static EntityManager defaultEntityManager;

    public static MetaEntity loadFromEntityManager(UUID e){
        MetaEntity metaEntity = new MetaEntity(e);
        return metaEntity;
    }

    public UUID entity = null;

    public EntityManager parentEntityManager;

    public MetaEntity(){
        if( defaultEntityManager == null )
            throw new IllegalArgumentException( "There is no global EntityManager; create a new EntityManager before creating Entity's" );

        parentEntityManager = defaultEntityManager;

        entity = defaultEntityManager.createEntity();
    }

    protected MetaEntity( UUID e ){
        if( defaultEntityManager == null )
            throw new IllegalArgumentException( "There is no global EntityManager; create a new EntityManager before creating Entity's" );

        parentEntityManager = defaultEntityManager;

        entity = e;
    }

    public MetaEntity( Component... components ){
        this(); // takes care of getting the initial "entity" part

        for( Component c : components ){
            this.add( c );
        }
    }

    public void add( Component c ){
        parentEntityManager.addComponent( entity, c );
    }

    public <T extends Component> T get( Class<T> type ){
        return parentEntityManager.getComponent( entity, type );
    }

    public <T extends Component> boolean has( Class<T> type ){
        return null != get( type );
    }

    public List<? extends Component> getAll(){
        return parentEntityManager.getAllComponentsOnEntity( entity );
    }

    public void removeAll(){
        for( Component c : getAll() ){
            remove(c);
        }
    }

    public <T extends Component> void remove( Component c ){
        parentEntityManager.removeComponent( entity, c );
    }

    @Override public String toString(){
        StringBuffer sb = new StringBuffer();
        for( Component c : parentEntityManager.getAllComponentsOnEntity( entity ) ){
            if( sb.length() > 0 )
                sb.append(  ", " );
            sb.append( c.toString() );
        }
        return "Entity["+entity+"]("+sb.toString()+")";
    }

    public void kill(){
        parentEntityManager.killEntity( entity );
    }
}